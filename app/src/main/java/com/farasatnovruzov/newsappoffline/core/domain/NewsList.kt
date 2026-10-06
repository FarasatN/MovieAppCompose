package com.farasatnovruzov.newsappoffline.core.domain

import kotlinx.serialization.Serializable

data class NewsList(
    val nextPage: String?,
    val results: List<News?>?,
    val status: String?,
    val totalResults: Int?
)