package com.farasatnovruzov.newsappoffline.core.domain

data class NewsList(
    val nextPage: String?,
    val results: List<Article?>?,
    val status: String?,
    val totalResults: Int?
)