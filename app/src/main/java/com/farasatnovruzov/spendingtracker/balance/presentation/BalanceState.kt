package com.farasatnovruzov.spendingtracker.balance.presentation

import androidx.compose.runtime.Immutable

@Immutable
data class BalanceState(
    val balance: Double = 0.0
)