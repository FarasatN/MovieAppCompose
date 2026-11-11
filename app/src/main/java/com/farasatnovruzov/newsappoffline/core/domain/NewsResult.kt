package com.farasatnovruzov.newsappoffline.core.domain

sealed interface NewsResult<out T> {
    data class Success<out T>(val data: T) : NewsResult<T>
    data class Error(val error: NewsError) : NewsResult<Nothing>
}
