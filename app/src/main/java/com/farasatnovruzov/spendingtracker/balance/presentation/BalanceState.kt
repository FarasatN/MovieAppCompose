package com.farasatnovruzov.spendingtracker.balance.presentation

import androidx.compose.runtime.Immutable

@Immutable
data class BalanceState(
    // Mətn kimi saxlanır (yazarkən "1." kimi aralıq halları pozulmasın)
    val balanceText: String = ""
)
