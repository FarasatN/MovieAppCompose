package com.farasatnovruzov.spendingtracker.spending_overview.presentation

sealed interface SpendingOverviewAction {
    data class OnDateChange(val newDate: Int): SpendingOverviewAction
    data class OnDeleteSpending(val spendingId: Int): SpendingOverviewAction
}
