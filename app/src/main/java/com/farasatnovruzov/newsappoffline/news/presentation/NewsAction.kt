package com.farasatnovruzov.newsappoffline.news.presentation

import com.farasatnovruzov.newsappoffline.core.domain.NewsCategory
import com.farasatnovruzov.newsappoffline.core.domain.NewsLanguage

sealed interface NewsAction {
    data object Paginate : NewsAction
    data class OnSearchQueryChange(val query: String) : NewsAction
    data class OnLanguageSelect(val language: NewsLanguage) : NewsAction
    data class OnCategorySelect(val category: NewsCategory) : NewsAction
    data object ClearSearch : NewsAction
    data object Refresh : NewsAction
}
