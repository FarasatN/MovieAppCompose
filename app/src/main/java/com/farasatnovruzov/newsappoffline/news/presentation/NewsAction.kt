package com.farasatnovruzov.newsappoffline.news.presentation

sealed interface NewsAction {
    data object Paginate: NewsAction
}
