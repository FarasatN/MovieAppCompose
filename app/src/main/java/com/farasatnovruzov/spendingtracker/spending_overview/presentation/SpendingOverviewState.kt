package com.farasatnovruzov.spendingtracker.spending_overview.presentation

import androidx.compose.runtime.Immutable
import com.farasatnovruzov.spendingtracker.core.domain.Spending
import java.time.LocalDate

@Immutable
data class SpendingOverviewState(
    val spendingList: List<Spending> = emptyList(),
    val datesList: List<LocalDate> = emptyList(),
    val balance: Double = 0.0,
    val pickedDate: LocalDate = LocalDate.now()
)
