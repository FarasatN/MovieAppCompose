package com.farasatnovruzov.spendingtracker.core.di

import android.content.Context
import androidx.room.Room
import androidx.datastore.preferences.preferencesDataStore
import com.farasatnovruzov.spendingtracker.core.data.RoomSpendingDataSource
import com.farasatnovruzov.spendingtracker.core.data.local.CoreRepositoryImpl
import com.farasatnovruzov.spendingtracker.core.data.local.SpendingDatabase
import com.farasatnovruzov.spendingtracker.core.domain.CoreRepository
import com.farasatnovruzov.spendingtracker.core.domain.LocalSpendingDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val Context.dataStore by preferencesDataStore(name = "spending_tracker_preferences")

val coreModule = module{
    single {
        Room.databaseBuilder(
            androidApplication(),
            SpendingDatabase::class.java,
            "spending_database_db"
        ).build()
    }

    single {
        get<SpendingDatabase>().dao
    }

    single {
        androidContext().dataStore
    }

    singleOf(::RoomSpendingDataSource).bind<LocalSpendingDataSource>()
    singleOf(::CoreRepositoryImpl).bind<CoreRepository>()
}