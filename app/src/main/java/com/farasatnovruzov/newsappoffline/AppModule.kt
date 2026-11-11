package com.farasatnovruzov.newsappoffline

import com.farasatnovruzov.movieappcompose.BuildConfig
import com.farasatnovruzov.newsappoffline.core.di.ApiKeysQualifier
import org.koin.dsl.module

val appModule = module {
    // BuildConfig.NEWS_API_KEY: vergüllə ayrılmış açarlar (local.properties-dən gəlir)
    single(ApiKeysQualifier) {
        BuildConfig.NEWS_API_KEY
            .split(",")
            .map { it.trim().removeSurrounding("\"") }
            .filter { it.isNotBlank() }
    }
}
