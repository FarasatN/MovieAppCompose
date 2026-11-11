package com.farasatnovruzov.newsappoffline.core.data.remote

import android.util.Log
import com.farasatnovruzov.movieappcompose.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {

    private const val LOG_TAG = "NewsHttp"
    private const val REQUEST_TIMEOUT_MS = 15_000L

    private val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }

    fun create(): HttpClient = HttpClient(OkHttp) {
        expectSuccess = true

        install(HttpTimeout) { requestTimeoutMillis = REQUEST_TIMEOUT_MS }

        engine {
            config { retryOnConnectionFailure(true) }
        }

        install(ContentNegotiation) { json(json) }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d(LOG_TAG, message)
                }
            }
            // Release-də heç nə yazılmır; debug-da yalnız sorğu/cavab xətti (gövdə yox).
            level = if (BuildConfig.DEBUG) LogLevel.INFO else LogLevel.NONE
            sanitizeHeader { header -> header.equals(NewsApiConfig.HEADER_API_KEY, ignoreCase = true) }
        }
    }
}
