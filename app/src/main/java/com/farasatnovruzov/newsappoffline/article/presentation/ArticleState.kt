package com.farasatnovruzov.newsappoffline.article.presentation

import com.farasatnovruzov.newsappoffline.core.domain.Article

data class ArticleState(
    val isLoading: Boolean = false,
    val articles: Article? = null,
    val isError: Boolean = false
//    val searchQuery: String = "",

)
