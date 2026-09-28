package com.farasatnovruzov.spendingtracker.spending_overview.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.farasatnovruzov.spendingtracker.spending_overview.presentation.SpendingOverviewViewModel


val spendingOverviewModule = module{
    viewModel {
        SpendingOverviewViewModel(get(), get())
    }
}