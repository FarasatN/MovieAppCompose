package com.farasatnovruzov.newsappoffline.core.data

import com.farasatnovruzov.newsappoffline.core.data.local.room.ArticlesDao
import com.farasatnovruzov.newsappoffline.core.domain.Article
import com.farasatnovruzov.newsappoffline.core.domain.NewsList
import com.farasatnovruzov.newsappoffline.core.domain.NewsRepository
import com.farasatnovruzov.newsappoffline.core.domain.NewsResult
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow

class NewsRepositoryImpl(
    private  val httpClient: HttpClient,
    private val dao: ArticlesDao
): NewsRepository {

    private val tag = "NewsRepository"
    private val baseUrl = "https://newsdata.io/api/1/latest"
    private val apiKey = "pub_afa06c3d3e9f4d75a0fde98a6fe00010"

//    private suspend fun getLocalNews(nextPage: String?): NewsList{
//        val localNews = dao.getArticleList()
//        println(tag+ " getLocalNews "+localNews.size+" nextPage: "+nextPage)
//        val newsList =  NewsList(
//            nextPage = nextPage,
//            articles = localNews.map{it.toArticle()}
//                    )
//        return newsList
//    }
    override suspend fun getNews(): Flow<NewsResult<NewsList>> {
        TODO("Not yet implemented")
    }

    override suspend fun paginate(nextPage: String): Flow<NewsResult<NewsList>> {
        TODO("Not yet implemented")
    }

    override suspend fun getArticle(articleId: String): Flow<NewsResult<Article>> {
        TODO("Not yet implemented")
    }


}