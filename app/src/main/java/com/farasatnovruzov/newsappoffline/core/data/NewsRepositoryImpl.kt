package com.farasatnovruzov.newsappoffline.core.data

import com.farasatnovruzov.movieappcompose.R
import com.farasatnovruzov.newsappoffline.core.data.local.room.ArticlesDao
import com.farasatnovruzov.newsappoffline.core.data.remote.NewsApiConfig
import com.farasatnovruzov.newsappoffline.core.data.remote.NewsListDto
import com.farasatnovruzov.newsappoffline.core.domain.Article
import com.farasatnovruzov.newsappoffline.core.domain.NewsList
import com.farasatnovruzov.newsappoffline.core.domain.NewsRepository
import com.farasatnovruzov.newsappoffline.core.domain.NewsResult
import com.farasatnovruzov.newsappoffline.core.util.ResourceProvider
import com.farasatnovruzov.newsappoffline.core.util.escapeSqlLike
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.nio.channels.UnresolvedAddressException
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.cancellation.CancellationException

class NewsRepositoryImpl(
    private val httpClient: HttpClient,
    private val dao: ArticlesDao,
    private val apiKeys: List<String>,
    private val resourceProvider: ResourceProvider
) : NewsRepository {

    private val currentKeyIndex = AtomicInteger(0)

    private fun getCurrentApiKey(): String {
        if (apiKeys.isEmpty()) return ""
        val index = currentKeyIndex.get() % apiKeys.size
        return apiKeys[index.coerceAtLeast(0)]
    }

    private fun rotateApiKey() {
        if (apiKeys.size > 1) {
            currentKeyIndex.incrementAndGet()
        }
    }

    private suspend fun <T> executeWithKeyRotation(block: suspend (apiKey: String) -> T): T {
        val maxAttempts = if (apiKeys.isNotEmpty()) apiKeys.size else 1
        var attempts = 0
        var lastError: Exception? = null

        while (attempts < maxAttempts) {
            val key = getCurrentApiKey()
            try {
                return block(key)
            } catch (e: ClientRequestException) {
                lastError = e
                if (e.response.status.value in listOf(400, 401, 403, 422, 429)) {
                    rotateApiKey()
                    attempts++
                } else {
                    throw e
                }
            } catch (e: CancellationException) {
                throw e // Korupsiya ləğv olunubsa, dərhal yuxarı ötürülür
            } catch (e: Exception) {
                if (isNetworkException(e)) {
                    throw e
                }

                lastError = e
                rotateApiKey()
                attempts++
            }
        }
        throw lastError ?: IllegalStateException(resourceProvider.getString(R.string.all_api_keys_exhausted))
    }

    private fun isNetworkException(e: Throwable): Boolean {
        return e is IOException || e is UnresolvedAddressException
    }

    // --- MƏRKƏZLƏŞDİRİLMİŞ ŞƏBƏKƏ SORĞUSU VƏ STATUS YOXLAMASI ---
    private suspend fun fetchNews(
        block: HttpRequestBuilder.() -> Unit
    ): NewsListDto {
        return executeWithKeyRotation { apiKey ->
            val response: NewsListDto = httpClient.get(NewsApiConfig.BASE_URL) {
                parameter("apikey", apiKey)
                block()
            }.body()

            if (response.status == "error") {
                throw Exception("API Quota/Error: ${response.status}")
            }
            response
        }
    }

    // --- TƏKRarlanan TRY/CATCH-İ ARADAN QALDIRAN KÖMƏKCİ FUNKSİYA ---
    private suspend fun <T> safeRemoteCall(block: suspend () -> T): T? {
        return try {
            block()
        } catch (e: CancellationException) {
            throw e
        } catch (_: Exception) {
            null
        }
    }

    private suspend fun getLocalNews(languageCode: String, nextPage: String?): NewsList {
        val localNews = dao.getArticleList(languageCode)
        return NewsList(
            nextPage = nextPage,
            status = "ok",
            totalResults = localNews.size,
            articles = localNews.map { it.toArticle() }
        )
    }

    private suspend fun getRemoteNews(nextPage: String?, languageCode: String, category: String?): NewsList {
        val newsListDto = fetchNews {
            parameter("language", languageCode)
            if (!category.isNullOrBlank()) parameter("category", category)
            if (nextPage != null) parameter("page", nextPage)
        }
        return newsListDto.toNewsList()
    }

    override fun getNews(languageCode: String, category: String?): Flow<NewsResult<NewsList>> {
        return flow {
            // 1. Əvvəlcə internetdən çəkməyə çalışırıq
            val remoteNewsList = safeRemoteCall {
                getRemoteNews(null, languageCode, category)
            }

            // 2. Əgər internet uğurludursa, bazanı yeniləyib göstəririk
            remoteNewsList?.let { newsList ->
                if (!newsList.articles.isNullOrEmpty()) {
                    val entities = newsList.articles.mapNotNull {
                        it?.toArticleEntity()?.copy(language = languageCode)
                    }
                    dao.clearAndUpsert(languageCode, entities)
                    emit(NewsResult.Success(newsList))
                    return@flow
                }
            }

            // 3. İNTERNET YOXDURSA VƏ YA XƏTA BAŞ VERDİRSƏ: Yerli bazadan oxuyuruq
            val localNewsList = getLocalNews(languageCode, null)

            // Sərt yoxlama: Yerli bazada məlumat varsa, dərhal emit edirik
            if (!localNewsList.articles.isNullOrEmpty()) {
                emit(NewsResult.Success(localNewsList))
                return@flow
            }

            // 4. Həm internet, həm də yerli baza tamamilə boşdursa xəta veririk
            emit(NewsResult.Error(resourceProvider.getString(R.string.no_data_available)))
        }
    }
    override fun paginate(nextPage: String, languageCode: String, category: String?): Flow<NewsResult<NewsList>> {
        return flow {
            val remoteNewsList = safeRemoteCall {
                getRemoteNews(nextPage, languageCode, category)
            }

            remoteNewsList?.let { newsList ->
                val entities = newsList.articles?.mapNotNull {
                    it?.toArticleEntity()?.copy(language = languageCode)
                } ?: emptyList()
                dao.upsertArticleList(entities)
                emit(NewsResult.Success(newsList))
                return@flow
            }

            emit(NewsResult.Error(resourceProvider.getString(R.string.pagination_error)))
        }
    }

    override fun searchNews(query: String, languageCode: String, category: String?): Flow<NewsResult<List<Article>>> {
        return flow {
            val safeQuery = query.escapeSqlLike()

            val localResults = dao.searchArticle(safeQuery).map { it.toArticle() }
            if (localResults.isNotEmpty()) {
                emit(NewsResult.Success(localResults))
            }

            try {
                val remoteNewsListDto = fetchNews {
                    parameter("q", query)
                    parameter("language", languageCode)
                    if (!category.isNullOrBlank()) parameter("category", category)
                }

                val remoteArticles = remoteNewsListDto.results?.mapNotNull { it?.toArticle() } ?: emptyList()
                if (remoteArticles.isNotEmpty()) {
                    dao.upsertArticleList(remoteArticles.map {
                        it.toArticleEntity().copy(language = languageCode)
                    })
                    val updatedLocalResults = dao.searchArticle(safeQuery).map { it.toArticle() }
                    emit(NewsResult.Success(updatedLocalResults))
                } else if (localResults.isEmpty()) {
                    emit(NewsResult.Success(emptyList()))
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                if (localResults.isEmpty()) {
                    emit(NewsResult.Error(e.localizedMessage ?: resourceProvider.getString(R.string.search_failed)))
                }
            }
        }
    }

    override fun getArticle(articleId: String): Flow<NewsResult<Article>> {
        return flow {
            dao.getArticle(articleId)?.let { article ->
                emit(NewsResult.Success(article.toArticle()))
                return@flow
            }

            try {
                val remoteArticle = fetchNews {
                    parameter("id", articleId)
                }

                val firstArticle = remoteArticle.results?.firstOrNull()?.toArticle()
                if (firstArticle != null) {
                    dao.upsertArticleList(listOf(firstArticle.toArticleEntity()))
                    emit(NewsResult.Success(firstArticle))
                } else {
                    emit(NewsResult.Error(resourceProvider.getString(R.string.article_not_found)))
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                emit(NewsResult.Error(e.localizedMessage ?: resourceProvider.getString(R.string.failed_to_load_article)))
            }
        }
    }
}