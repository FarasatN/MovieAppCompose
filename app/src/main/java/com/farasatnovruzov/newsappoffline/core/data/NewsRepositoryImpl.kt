package com.farasatnovruzov.newsappoffline.core.data

import com.farasatnovruzov.newsappoffline.core.data.local.room.ArticlesDao
import com.farasatnovruzov.newsappoffline.core.data.remote.NewsApiConfig
import com.farasatnovruzov.newsappoffline.core.data.remote.NewsListDto
import com.farasatnovruzov.newsappoffline.core.domain.Article
import com.farasatnovruzov.newsappoffline.core.domain.NewsList
import com.farasatnovruzov.newsappoffline.core.domain.NewsRepository
import com.farasatnovruzov.newsappoffline.core.domain.NewsResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.cancellation.CancellationException

class NewsRepositoryImpl(
    private val httpClient: HttpClient,
    private val dao: ArticlesDao
) : NewsRepository {

    private val currentKeyIndex = AtomicInteger(0)

    private fun getCurrentApiKey(): String {
        val keys = NewsApiConfig.API_KEYS
        if (keys.isEmpty()) return ""
        val index = currentKeyIndex.get() % keys.size
        return keys[index.coerceAtLeast(0)]
    }

    private fun rotateApiKey() {
        val keys = NewsApiConfig.API_KEYS
        if (keys.size > 1) {
            currentKeyIndex.incrementAndGet()
        }
    }

    private suspend fun <T> executeWithKeyRotation(block: suspend (apiKey: String) -> T): T {
        val keys = NewsApiConfig.API_KEYS
        val maxAttempts = if (keys.isNotEmpty()) keys.size else 1
        var attempts = 0
        var lastError: Throwable? = null

        while (attempts < maxAttempts) {
            val key = getCurrentApiKey()
            try {
                return block(key)
            } catch (e: ClientRequestException) {
                lastError = e
                if (e.response.status.value in listOf(400, 422, 429)) {
                    rotateApiKey()
                    attempts++
                } else {
                    throw e
                }
            } catch (e: Throwable) {
                if (e is CancellationException) throw e
                lastError = e
                rotateApiKey()
                attempts++
            }
        }
        throw lastError ?: IllegalStateException("Bütün API key-lərin limiti dolub.")
    }

    private suspend fun getLocalNews(nextPage: String?): NewsList {
        val localNews = dao.getArticleList()
        return NewsList(
            nextPage = nextPage,
            status = "ok",
            totalResults = localNews.size,
            articles = localNews.map { it.toArticle() }
        )
    }

    private suspend fun getRemoteNews(nextPage: String?, languageCode: String, category: String?): NewsList {
        val newsListDto: NewsListDto = executeWithKeyRotation { apiKey ->
            val response: NewsListDto = httpClient.get(NewsApiConfig.BASE_URL) {
                parameter("apikey", apiKey)
                parameter("language", languageCode)
                if (!category.isNullOrBlank()) parameter("category", category)
                if (nextPage != null) parameter("page", nextPage)
            }.body()

            if (response.status == "error") {
                throw Exception("API Quota/Error: ${response.status}")
            }
            response
        }
        return newsListDto.toNewsList()
    }

    override suspend fun getNews(languageCode: String, category: String?): Flow<NewsResult<NewsList>> {
        return flow {
            val remoteNewsList = try {
                getRemoteNews(null, languageCode, category)
            } catch (e: Throwable) {
                if (e is CancellationException) throw e
                null
            }

            remoteNewsList?.let { newsList ->
                dao.clearDatabase()
                val entities = newsList.articles?.mapNotNull { it?.toArticleEntity() } ?: emptyList()
                dao.upsertArticleList(entities)
                emit(NewsResult.Success(getLocalNews(newsList.nextPage)))
                return@flow
            }

            val localNewsList = getLocalNews(null)
            if (localNewsList.articles?.isNotEmpty() == true) {
                emit(NewsResult.Success(localNewsList))
                return@flow
            }

            emit(NewsResult.Error("Məlumat tapılmadı"))
        }
    }

    override suspend fun paginate(nextPage: String, languageCode: String, category: String?): Flow<NewsResult<NewsList>> {
        return flow {
            val remoteNewsList = try {
                getRemoteNews(nextPage, languageCode, category)
            } catch (e: Throwable) {
                if (e is CancellationException) throw e
                null
            }

            remoteNewsList?.let { newsList ->
                val entities = newsList.articles?.mapNotNull { it?.toArticleEntity() } ?: emptyList()
                dao.upsertArticleList(entities)
                emit(NewsResult.Success(newsList))
                return@flow
            }

            emit(NewsResult.Error("Səhifələmə xətası"))
        }
    }

    override suspend fun searchNews(query: String, languageCode: String, category: String?): Flow<NewsResult<List<Article>>> {
        return flow {
            val localResults = dao.searchArticle(query).map { it.toArticle() }
            if (localResults.isNotEmpty()) {
                emit(NewsResult.Success(localResults))
            }

            try {
                val remoteNewsListDto: NewsListDto = executeWithKeyRotation { apiKey ->
                    val response: NewsListDto = httpClient.get(NewsApiConfig.BASE_URL) {
                        parameter("apikey", apiKey)
                        parameter("q", query)
                        parameter("language", languageCode)
                        if (!category.isNullOrBlank()) parameter("category", category)
                    }.body()

                    if (response.status == "error") {
                        throw Exception("API Quota/Error: ${response.status}")
                    }
                    response
                }

                val remoteArticles = remoteNewsListDto.results?.mapNotNull { it?.toArticle() } ?: emptyList()
                if (remoteArticles.isNotEmpty()) {
                    dao.upsertArticleList(remoteArticles.map { it.toArticleEntity() })
                    val updatedLocalResults = dao.searchArticle(query).map { it.toArticle() }
                    emit(NewsResult.Success(updatedLocalResults))
                } else if (localResults.isEmpty()) {
                    emit(NewsResult.Success(emptyList()))
                }
            } catch (e: Throwable) {
                if (e is CancellationException) throw e
                if (localResults.isEmpty()) {
                    emit(NewsResult.Error(e.localizedMessage ?: "Axtarış xətası baş verdi"))
                }
            }
        }
    }

    override suspend fun getArticle(articleId: String): Flow<NewsResult<Article>> {
        return flow {
            dao.getArticle(articleId)?.let { article ->
                emit(NewsResult.Success(article.toArticle()))
                return@flow
            }

            try {
                val remoteArticle: NewsListDto = executeWithKeyRotation { apiKey ->
                    val response: NewsListDto = httpClient.get(NewsApiConfig.BASE_URL) {
                        parameter("apikey", apiKey)
                        parameter("article_id", articleId)
                    }.body()

                    if (response.status == "error") {
                        throw Exception("API Quota/Error: ${response.status}")
                    }
                    response
                }

                val firstArticle = remoteArticle.results?.firstOrNull()?.toArticle()
                if (firstArticle != null) {
                    dao.upsertArticleList(listOf(firstArticle.toArticleEntity()))
                    emit(NewsResult.Success(firstArticle))
                } else {
                    emit(NewsResult.Error("Məqalə tapılmadı"))
                }
            } catch (e: Throwable) {
                if (e is CancellationException) throw e
                emit(NewsResult.Error(e.localizedMessage ?: "Məqaləni yükləmək mümkün olmadı"))
            }
        }
    }
}
