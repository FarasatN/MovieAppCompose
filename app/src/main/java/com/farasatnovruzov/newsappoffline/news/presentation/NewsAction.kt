package com.farasatnovruzov.newsappoffline.news.presentation

import com.farasatnovruzov.newsappoffline.core.domain.NewsLanguage

sealed interface NewsAction {
    data object Paginate : NewsAction
    data class OnSearchQueryChange(val query: String) : NewsAction
    data class OnLanguageSelect(val language: NewsLanguage) : NewsAction
    data object ClearSearch : NewsAction
    data object Refresh : NewsAction
}
