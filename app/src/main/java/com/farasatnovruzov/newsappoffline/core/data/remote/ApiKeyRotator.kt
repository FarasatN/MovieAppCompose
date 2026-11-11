package com.farasatnovruzov.newsappoffline.core.data.remote

import io.ktor.client.plugins.ClientRequestException
import java.util.concurrent.atomic.AtomicInteger

/**
 * Bir neçə API açarı arasında dairəvi (round-robin) rotasiya.
 * Yalnız açarla bağlı xətalarda (limit, etibarsız açar, API-nin "error" statusu) növbəti açara keçir.
 * Şəbəkə, 5xx, parse xətaları və coroutine ləğvi dərhal yuxarı ötürülür – açarı boş yerə yandırmır.
 */
class ApiKeyRotator(private val apiKeys: List<String>) {

    private val currentKeyIndex = AtomicInteger(0)

    suspend fun <T> execute(block: suspend (apiKey: String) -> T): T {
        val maxAttempts = apiKeys.size.coerceAtLeast(1)
        var lastError: Exception? = null

        repeat(maxAttempts) {
            try {
                return block(currentKey())
            } catch (e: ClientRequestException) {
                if (e.response.status.value !in ROTATABLE_STATUS_CODES) throw e
                lastError = e
                rotate()
            } catch (e: NewsApiException) {
                lastError = e
                rotate()
            }
        }
        throw lastError ?: IllegalStateException("All API keys are exhausted")
    }

    private fun currentKey(): String {
        if (apiKeys.isEmpty()) return ""
        return apiKeys[Math.floorMod(currentKeyIndex.get(), apiKeys.size)]
    }

    private fun rotate() {
        if (apiKeys.size > 1) currentKeyIndex.incrementAndGet()
    }

    private companion object {
        val ROTATABLE_STATUS_CODES = setOf(400, 401, 403, 422, 429)
    }
}
