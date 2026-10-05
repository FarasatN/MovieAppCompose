package com.farasatnovruzov.spendingtracker.balance.presentation

sealed interface BalanceAction {
    data class OnBalanceChanged(val newBalance: String) : BalanceAction
    data object OnBalanceSaved : BalanceAction
}
