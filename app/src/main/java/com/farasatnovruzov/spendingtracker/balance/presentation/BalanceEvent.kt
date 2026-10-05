package com.farasatnovruzov.spendingtracker.balance.presentation

sealed interface BalanceEvent {
    // Balans DataStore-a TAM yazıldıqdan sonra göndərilir
    data object Saved : BalanceEvent
}
