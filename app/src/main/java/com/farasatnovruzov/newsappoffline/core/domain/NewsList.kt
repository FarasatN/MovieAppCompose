package com.farasatnovruzov.newsappoffline.core.domain

data class NewsList(
    val articles: List<Article>,
    val nextPage: String?
)
