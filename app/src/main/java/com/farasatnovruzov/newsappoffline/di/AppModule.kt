package com.farasatnovruzov.newsappoffline.di

import com.farasatnovruzov.movieappcompose.BuildConfig
import com.farasatnovruzov.newsappoffline.core.util.ResourceProvider
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single(named("ApiKeys")) {
        BuildConfig.NEWS_API_KEY
            .split(",")
            .map { it.trim().removeSurrounding("\"") }
            .filter { it.isNotBlank() }
    }

    single { ResourceProvider(androidContext()) }
}