package com.farasatnovruzov.newsappoffline.news.presentation

import com.farasatnovruzov.newsappoffline.core.domain.Article
import com.farasatnovruzov.newsappoffline.core.domain.NewsCategory
import com.farasatnovruzov.newsappoffline.core.domain.NewsError
import com.farasatnovruzov.newsappoffline.core.domain.NewsLanguage

data class NewsState(
    val articleList: List<Article> = emptyList(),
    val searchArticleList: List<Article> = emptyList(),
    val nextPage: String? = null,
    val isLoading: Boolean = false,
    val isSearchingLoading: Boolean = false,
    val error: NewsError? = null,
    val searchQuery: String = "",
    val selectedLanguage: NewsLanguage = NewsLanguage.AZERBAIJANI,
    val selectedCategory: NewsCategory = NewsCategory.ALL
) {
    val isError: Boolean
        get() = error != null

    val isSearchActive: Boolean
        get() = searchQuery.isNotBlank()

    val displayedArticles: List<Article>
        get() = if (isSearchActive) searchArticleList else articleList
}
