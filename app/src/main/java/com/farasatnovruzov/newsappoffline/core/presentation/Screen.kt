package com.farasatnovruzov.newsappoffline.core.presentation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {

    @kotlinx.serialization.Serializable
    data object News : Screen

    @kotlinx.serialization.Serializable
    data class Article(val articleId: String) : Screen

}