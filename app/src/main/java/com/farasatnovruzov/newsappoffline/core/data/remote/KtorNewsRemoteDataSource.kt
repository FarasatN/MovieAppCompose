package com.farasatnovruzov.newsappoffline.core.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter

class KtorNewsRemoteDataSource(
    private val httpClient: HttpClient,
    private val apiKeyRotator: ApiKeyRotator
) : NewsRemoteDataSource {

    override suspend fun fetchNews(
        nextPage: String?,
        languageCode: String,
        category: String?
    ): NewsListDto = request {
        parameter(NewsApiConfig.PARAM_LANGUAGE, languageCode)
        if (!category.isNullOrBlank()) parameter(NewsApiConfig.PARAM_CATEGORY, category)
        if (nextPage != null) parameter(NewsApiConfig.PARAM_PAGE, nextPage)
    }

    override suspend fun searchNews(
        query: String,
        languageCode: String,
        category: String?
    ): NewsListDto = request {
        parameter(NewsApiConfig.PARAM_QUERY, query)
        parameter(NewsApiConfig.PARAM_LANGUAGE, languageCode)
        if (!category.isNullOrBlank()) parameter(NewsApiConfig.PARAM_CATEGORY, category)
    }

    override suspend fun fetchArticle(articleId: String): NewsListDto = request {
        parameter(NewsApiConfig.PARAM_ARTICLE_ID, articleId)
    }

    /** Açar rotasiyası, autentifikasiya header-i və "status=error" yoxlaması bir yerdə (DRY). */
    private suspend fun request(configure: HttpRequestBuilder.() -> Unit): NewsListDto =
        apiKeyRotator.execute { apiKey ->
            val response: NewsListDto = httpClient.get(NewsApiConfig.BASE_URL) {
                header(NewsApiConfig.HEADER_API_KEY, apiKey)
                configure()
            }.body()

            if (response.status == NewsApiConfig.STATUS_ERROR) {
                throw NewsApiException("API Quota/Error: ${response.status}")
            }
            response
        }
}
