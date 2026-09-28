package com.farasatnovruzov.spendingtracker

import android.app.Application
import com.farasatnovruzov.spendingtracker.balance.di.balanceModule
import com.farasatnovruzov.spendingtracker.core.di.coreModule
import com.farasatnovruzov.spendingtracker.spending_overview.di.spendingOverviewModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                // Yaradacağınız Koin modullarını bura əlavə edəcəksiniz
                // appModule, viewModelModule, repositoryModule
                coreModule,
                balanceModule,
                spendingOverviewModule
            )
        }
    }
}