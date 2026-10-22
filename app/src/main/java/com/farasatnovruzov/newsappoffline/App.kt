package com.farasatnovruzov.newsappoffline

import android.app.Application
import com.farasatnovruzov.newsappoffline.article.di.articleModule
import com.farasatnovruzov.newsappoffline.core.di.coreModule
import com.farasatnovruzov.newsappoffline.news.di.newsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(coreModule, newsModule, articleModule)
        }
    }
}