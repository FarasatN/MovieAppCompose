package com.farasatnovruzov.newsappoffline.core.domain

import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNews(languageCode: String = "az", category: String? = null): Flow<NewsResult<NewsList>>
    suspend fun paginate(nextPage: String, languageCode: String = "az", category: String? = null): Flow<NewsResult<NewsList>>
    suspend fun searchNews(query: String, languageCode: String = "az", category: String? = null): Flow<NewsResult<List<Article>>>
    suspend fun getArticle(articleId: String): Flow<NewsResult<Article>>
}
