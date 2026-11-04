package com.farasatnovruzov.newsappoffline

import com.farasatnovruzov.movieappcompose.BuildConfig
import com.farasatnovruzov.newsappoffline.core.util.ResourceProvider
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

// app modulunda (və ya di paketində)
val appModule = module {
    single(named("ApiKeys")) {
        BuildConfig.NEWS_API_KEY
            .split(",")
            .map { it.trim().removeSurrounding("\"") }
            .filter { it.isNotBlank() }
    }

    single { ResourceProvider(androidContext()) }
}