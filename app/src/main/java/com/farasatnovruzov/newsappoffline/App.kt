package com.farasatnovruzov.newsappoffline

import android.app.Application
import com.farasatnovruzov.newsappoffline.article.di.articleModule
import com.farasatnovruzov.newsappoffline.core.di.coreModules
import com.farasatnovruzov.newsappoffline.news.di.newsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(listOf(appModule, newsModule, articleModule) + coreModules)
        }
    }
}
