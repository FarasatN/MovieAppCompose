package com.farasatnovruzov.spendingtracker.spending_details.presentation

import androidx.compose.runtime.Immutable
import java.time.ZonedDateTime

@Immutable
data class SpendingDetailsState(
    val spendingId: Int? = null,
    val name: String = "",
    // Rəqəm sahələri MƏTN kimi saxlanır: "1." və ya "0.0" yazarkən istifadəçini pozmur
    val price: String = "",
    val kilograms: String = "",
    val quantity: String = "",
    val dateTimeUtc: ZonedDateTime? = null,
)
