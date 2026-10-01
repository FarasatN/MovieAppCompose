package com.farasatnovruzov.spendingtracker.spending_details.di

import com.farasatnovruzov.spendingtracker.spending_details.domain.UpsertSpendingUseCase
import com.farasatnovruzov.spendingtracker.spending_details.presentation.SpendingDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val spendingDetailsModule = module {
    single {
        UpsertSpendingUseCase(get())
    }
    viewModel {
        SpendingDetailsViewModel(get(), get())
    }
}