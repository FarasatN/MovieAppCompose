package com.farasatnovruzov.spendingtracker.spending_details.presentation

sealed interface SpendingDetailsEvent{

    data object SaveSuccess: SpendingDetailsEvent
    data object SaveFailed: SpendingDetailsEvent
}