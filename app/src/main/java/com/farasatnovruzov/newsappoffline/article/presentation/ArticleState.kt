package com.farasatnovruzov.newsappoffline.article.presentation

import com.farasatnovruzov.newsappoffline.core.domain.Article
import com.farasatnovruzov.newsappoffline.core.domain.NewsError

data class ArticleState(
    val isLoading: Boolean = false,
    val article: Article? = null,
    val error: NewsError? = null
) {
    val isError: Boolean
        get() = error != null
}
