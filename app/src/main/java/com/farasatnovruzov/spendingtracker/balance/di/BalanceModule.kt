package com.farasatnovruzov.spendingtracker.balance.di

import com.farasatnovruzov.spendingtracker.balance.presentation.BalanceViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val balanceModule = module {
    viewModel {
        BalanceViewModel(get())
    }
}