package com.farasatnovruzov.newsappoffline.core.di

import androidx.room.Room
import com.farasatnovruzov.newsappoffline.core.data.NewsRepositoryImpl
import com.farasatnovruzov.newsappoffline.core.data.local.room.ArticleDatabase
import com.farasatnovruzov.newsappoffline.core.data.remote.ApiKeyRotator
import com.farasatnovruzov.newsappoffline.core.data.remote.HttpClientFactory
import com.farasatnovruzov.newsappoffline.core.data.remote.KtorNewsRemoteDataSource
import com.farasatnovruzov.newsappoffline.core.data.remote.NewsRemoteDataSource
import com.farasatnovruzov.newsappoffline.core.domain.NewsRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

private const val DATABASE_NAME = "article_db.db"

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            ArticleDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }
    single { get<ArticleDatabase>().dao }
}

val networkModule = module {
    single { HttpClientFactory.create() }
    single { ApiKeyRotator(apiKeys = get(ApiKeysQualifier)) }
    single<NewsRemoteDataSource> {
        KtorNewsRemoteDataSource(
            httpClient = get(),
            apiKeyRotator = get()
        )
    }
}

val repositoryModule = module {
    single<NewsRepository> {
        NewsRepositoryImpl(
            remoteDataSource = get(),
            dao = get()
        )
    }
}

val coreModules = listOf(databaseModule, networkModule, repositoryModule)
