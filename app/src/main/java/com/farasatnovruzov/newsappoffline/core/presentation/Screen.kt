package com.farasatnovruzov.newsappoffline.core.presentation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {

    @Serializable
    data object News : Screen

    @Serializable
    data class Article(val articleId: String) : Screen
}
