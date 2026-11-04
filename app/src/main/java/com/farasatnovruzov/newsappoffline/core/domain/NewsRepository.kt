package com.farasatnovruzov.newsappoffline.core.domain

import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(languageCode: String, category: String?): Flow<NewsResult<NewsList>>
    fun paginate(nextPage: String, languageCode: String, category: String?): Flow<NewsResult<NewsList>>
    fun searchNews(query: String, languageCode: String, category: String?): Flow<NewsResult<List<Article>>>
    fun getArticle(articleId: String): Flow<NewsResult<Article>>
}

