package com.farasatnovruzov.newsappoffline

import com.farasatnovruzov.newsappoffline.article.di.articleModule
import com.farasatnovruzov.newsappoffline.core.di.coreModule
import com.farasatnovruzov.newsappoffline.news.di.newsModule
import io.ktor.client.HttpClient
import org.junit.Test
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class KoinCheckTest {

    @Test
    fun testHttpClient() {
        stopKoin()
        val koinApp = startKoin {
            modules(coreModule, newsModule, articleModule)
        }
        val client = koinApp.koin.get<HttpClient>()
        println("HttpClient initialized: $client")
        stopKoin()
    }
}
