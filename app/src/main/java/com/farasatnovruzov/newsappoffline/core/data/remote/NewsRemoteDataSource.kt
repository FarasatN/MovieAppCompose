package com.farasatnovruzov.newsappoffline.core.data.remote

interface NewsRemoteDataSource {
    suspend fun fetchNews(nextPage: String?, languageCode: String, category: String?): NewsListDto
    suspend fun searchNews(query: String, languageCode: String, category: String?): NewsListDto
    suspend fun fetchArticle(articleId: String): NewsListDto
}
