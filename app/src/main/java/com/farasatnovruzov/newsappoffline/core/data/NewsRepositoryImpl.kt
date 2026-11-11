package com.farasatnovruzov.newsappoffline.core.data

import com.farasatnovruzov.newsappoffline.core.data.local.room.ArticlesDao
import com.farasatnovruzov.newsappoffline.core.data.mapper.toArticle
import com.farasatnovruzov.newsappoffline.core.data.mapper.toArticleEntity
import com.farasatnovruzov.newsappoffline.core.data.mapper.toNewsList
import com.farasatnovruzov.newsappoffline.core.data.remote.NewsRemoteDataSource
import com.farasatnovruzov.newsappoffline.core.domain.Article
import com.farasatnovruzov.newsappoffline.core.domain.NewsError
import com.farasatnovruzov.newsappoffline.core.domain.NewsList
import com.farasatnovruzov.newsappoffline.core.domain.NewsRepository
import com.farasatnovruzov.newsappoffline.core.domain.NewsResult
import com.farasatnovruzov.newsappoffline.core.util.escapeSqlLike
import com.farasatnovruzov.newsappoffline.core.util.suspendRunCatching
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

/**
 * Offline-first repository: əvvəl şəbəkə, alınmasa lokal cache.
 * Gözlənilməz xətalar (məs. DB) tətbiqi çökdürmür, [NewsResult.Error] kimi çatdırılır.
 */ 
class NewsRepositoryImpl(
    private val remoteDataSource: NewsRemoteDataSource,
    private val dao: ArticlesDao
) : NewsRepository {

    override fun getNews(languageCode: String, category: String?): Flow<NewsResult<NewsList>> =
        flow<NewsResult<NewsList>> {
            // 1. Əvvəlcə internetdən çəkməyə çalışırıq
            val remoteNews = suspendRunCatching {
                remoteDataSource.fetchNews(nextPage = null, languageCode = languageCode, category = category)
            }.getOrNull()?.toNewsList()

            // 2. Uğurludursa: həmin dilin cache-ini tranzaksiya ilə yeniləyib göstəririk
            if (remoteNews != null && remoteNews.articles.isNotEmpty()) {
                dao.clearAndUpsert(languageCode, remoteNews.articles.map { it.toArticleEntity(languageCode) })
                emit(NewsResult.Success(remoteNews))
                return@flow
            }

            // 3. İnternet yoxdursa / boş cavab: lokal cache
            val localNews = getLocalNews(languageCode, category)
            if (localNews.articles.isNotEmpty()) {
                emit(NewsResult.Success(localNews))
            } else {
                // 4. Həm şəbəkə, həm cache boşdur
                emit(NewsResult.Error(NewsError.NoData))
            }
        }.catch { emit(NewsResult.Error(NewsError.NoData)) }

    override fun paginate(
        nextPage: String,
        languageCode: String,
        category: String?
    ): Flow<NewsResult<NewsList>> =
        flow<NewsResult<NewsList>> {
            val remoteNews = suspendRunCatching {
                remoteDataSource.fetchNews(nextPage = nextPage, languageCode = languageCode, category = category)
            }.getOrNull()?.toNewsList()

            if (remoteNews != null) {
                dao.upsertArticleList(remoteNews.articles.map { it.toArticleEntity(languageCode) })
                emit(NewsResult.Success(remoteNews))
            } else {
                emit(NewsResult.Error(NewsError.PaginationFailed))
            }
        }.catch { emit(NewsResult.Error(NewsError.PaginationFailed)) }

    override fun searchNews(
        query: String,
        languageCode: String,
        category: String?
    ): Flow<NewsResult<List<Article>>> =
        flow<NewsResult<List<Article>>> {
            val safeQuery = query.escapeSqlLike()

            // Lokal nəticə varsa dərhal göstəririk (sürətli cavab)
            val localResults = searchLocal(safeQuery, languageCode)
            if (localResults.isNotEmpty()) {
                emit(NewsResult.Success(localResults))
            }

            suspendRunCatching {
                remoteDataSource.searchNews(query = query, languageCode = languageCode, category = category)
            }.onSuccess { dto ->
                val remoteArticles = dto.toNewsList().articles
                if (remoteArticles.isNotEmpty()) {
                    dao.upsertArticleList(remoteArticles.map { it.toArticleEntity(languageCode) })
                    emit(NewsResult.Success(searchLocal(safeQuery, languageCode)))
                } else if (localResults.isEmpty()) {
                    emit(NewsResult.Success(emptyList()))
                }
            }.onFailure {
                if (localResults.isEmpty()) {
                    emit(NewsResult.Error(NewsError.SearchFailed))
                }
            }
        }.catch { emit(NewsResult.Error(NewsError.SearchFailed)) }

    override fun getArticle(articleId: String): Flow<NewsResult<Article>> =
        flow<NewsResult<Article>> {
            val cachedArticle = dao.getArticle(articleId)
            if (cachedArticle != null) {
                emit(NewsResult.Success(cachedArticle.toArticle()))
                return@flow
            }

            suspendRunCatching {
                remoteDataSource.fetchArticle(articleId)
            }.onSuccess { dto ->
                val article = dto.toNewsList().articles.firstOrNull()
                if (article != null) {
                    dao.upsertArticleList(listOf(article.toArticleEntity()))
                    emit(NewsResult.Success(article))
                } else {
                    emit(NewsResult.Error(NewsError.ArticleNotFound))
                }
            }.onFailure {
                emit(NewsResult.Error(NewsError.ArticleLoadFailed))
            }
        }.catch { emit(NewsResult.Error(NewsError.ArticleLoadFailed)) }

    private suspend fun getLocalNews(languageCode: String, category: String?): NewsList = NewsList(
        articles = dao.getArticleList(languageCode, category).map { it.toArticle() },
        nextPage = null
    )

    private suspend fun searchLocal(safeQuery: String, languageCode: String): List<Article> =
        dao.searchArticle(safeQuery, languageCode).map { it.toArticle() }
}
