package com.farasatnovruzov.newsappoffline.core.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class NewsListDto(
    val nextPage: String?,
    val results: List<ArticleDto?>?,
    val status: String?,
    val totalResults: Int?
)