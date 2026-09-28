package com.farasatnovruzov.spendingtracker.spending_overview.presentation

import java.time.LocalDate

sealed interface SpendingOverviewAction {
    data object LoadSpendingOverviewBalance: SpendingOverviewAction
    data class OnDateChange(val newDate: Int): SpendingOverviewAction
    data class OnDeleteSpending(val spendingId: Int): SpendingOverviewAction
}