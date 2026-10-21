package com.farasatnovruzov.newsappoffline.core.data

import com.farasatnovruzov.newsappoffline.core.data.local.room.ArticlesDao
import com.farasatnovruzov.newsappoffline.core.data.remote.NewsListDto
import com.farasatnovruzov.newsappoffline.core.domain.Article
import com.farasatnovruzov.newsappoffline.core.domain.NewsList
import com.farasatnovruzov.newsappoffline.core.domain.NewsRepository
import com.farasatnovruzov.newsappoffline.core.domain.NewsResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.cancellation.CancellationException

class NewsRepositoryImpl(
    private val httpClient: HttpClient,
    private val dao: ArticlesDao
) : NewsRepository {

    //https://newsdata.io/api/1/latest?apikey=pub_afa06c3d3e9f4d75a0fde98a6fe00010&language=az
    private val tag = "NewsRepository"
    private val baseUrl = "https://newsdata.io/api/1/latest"
    private val apiKey = "pub_afa06c3d3e9f4d75a0fde98a6fe00010"

    private suspend fun getLocalNews(nextPage: String?): NewsList {
        val localNews = dao.getArticleList()
        println(tag + " getLocalNews " + localNews.size + " nextPage: " + nextPage)
        val newsList = NewsList(
            nextPage = nextPage,
            status = "ok",
            totalResults = localNews.size,
            articles = localNews.map { it.toArticle() }
        )
        return newsList
    }

    private suspend fun getRemoteNews(nextPage: String?): NewsList {
        val newsListDto: NewsListDto = httpClient.get(baseUrl) {
            parameter("apikey", apiKey)
            parameter("language", "az")
            if (nextPage != null) parameter("page", nextPage)
        }.body()
        println(tag + " getRemoteNews " + newsListDto.results?.size + " nextPage: " + nextPage)
        return newsListDto.toNewsList()
    }


    override suspend fun getNews(): Flow<NewsResult<NewsList>> {
        return flow {
            val remoteNewsList = try {
                getRemoteNews(null)
            } catch (e: Exception) {
                e.printStackTrace()
                println(tag + " getNews remote exception " + e.message)
                null
            }

            remoteNewsList?.let {
                dao.clearDatabase()
                dao.upsertArticleList(remoteNewsList.articles?.map { it!!.toArticleEntity() }
                    ?: emptyList())
                emit(NewsResult.Success(getLocalNews(remoteNewsList.nextPage)))
                return@flow
            }

            val localNewsList = getLocalNews(null)
            if (localNewsList.articles?.isNotEmpty() == true) {
                emit(NewsResult.Success(localNewsList))
                return@flow
            }

            emit(NewsResult.Error("No data"))
        }
    }

    override suspend fun paginate(nextPage: String): Flow<NewsResult<NewsList>> {
        return flow {
            val remoteNewsList = try {
                getRemoteNews(nextPage)
            } catch (e: Exception) {
                e.printStackTrace()
                println(tag + " getNews remote exception " + e.message)
                null
            }

            remoteNewsList?.let {
//                dao.clearDatabase()
                dao.upsertArticleList(remoteNewsList.articles?.map { it!!.toArticleEntity() }
                    ?: emptyList())
//                emit(NewsResult.Success(getLocalNews(remoteNewsList.nextPage))) //can give duplicated error

                emit(NewsResult.Success(remoteNewsList))
                return@flow
            }

            emit(NewsResult.Error("No data"))
        }
    }

    override suspend fun getArticle(articleId: String): Flow<NewsResult<Article>> {
        return flow {
            dao.getArticle(articleId)?.let { article ->
                println(tag + " get local article data" + article.title)
                emit(NewsResult.Success(article.toArticle()))
                return@flow
            }

            try {
                val remoteArticle: NewsListDto = httpClient.get(baseUrl) {
                    parameter("apikey", apiKey)
//                    parameter("language", "az")
                    parameter("article_id", articleId)
                }.body()
                println(tag + " get article remote " + remoteArticle.results?.size)
                if (remoteArticle.results?.isNotEmpty() == true) {
                    emit(NewsResult.Success(remoteArticle.results[0]?.toArticle()))
                }else{
                    emit(NewsResult.Error("Can't load article"))
                }

            }catch (e: Exception){
                e.printStackTrace()
                if (e is CancellationException) throw e
                println(tag + " get article remote exception " + e.message)
                emit(NewsResult.Error("Can't load article"))
                return@flow
            }
        }
    }


}