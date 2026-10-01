package com.farasatnovruzov.spendingtracker.core.presentation.util

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {
    @Serializable
    data object SpendingOverview : Screen

    @Serializable
    data class SpendingDetails(val spendingId: Int? = -1) : Screen

    @Serializable
    data object Balance : Screen
}