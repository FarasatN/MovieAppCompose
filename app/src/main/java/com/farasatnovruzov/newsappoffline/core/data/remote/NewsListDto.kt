package com.farasatnovruzov.newsappoffline.core.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class NewsListDto(
    val nextPage: String? = null,
    val results: List<ArticleDto?>? = null,
    val status: String? = null,
    val totalResults: Int? = null
)
