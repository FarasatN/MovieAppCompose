package com.farasatnovruzov.newsappoffline.core.util

import kotlin.coroutines.cancellation.CancellationException

/**
 * [runCatching]-in coroutine-təhlükəsiz variantı: [CancellationException] udulmur,
 * yenidən atılır ki, structured concurrency pozulmasın.
 */
suspend inline fun <T> suspendRunCatching(block: suspend () -> T): Result<T> =
    try {
        Result.success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }
