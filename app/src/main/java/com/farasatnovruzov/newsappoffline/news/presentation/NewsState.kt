package com.farasatnovruzov.newsappoffline.news.presentation

import com.farasatnovruzov.newsappoffline.core.domain.Article

data class NewsState(
    val articleList: List<Article> = emptyList(),
    val nextPage: String? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)
