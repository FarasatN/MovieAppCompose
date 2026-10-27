package com.farasatnovruzov.newsappoffline.core.domain

import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNews(languageCode: String = "az"): Flow<NewsResult<NewsList>>
    suspend fun paginate(nextPage: String, languageCode: String = "az"): Flow<NewsResult<NewsList>>
    suspend fun searchNews(query: String, languageCode: String = "az"): Flow<NewsResult<List<Article>>>
    suspend fun getArticle(articleId: String): Flow<NewsResult<Article>>
}
