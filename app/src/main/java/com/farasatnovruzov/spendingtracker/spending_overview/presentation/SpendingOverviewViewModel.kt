package com.farasatnovruzov.spendingtracker.spending_overview.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farasatnovruzov.spendingtracker.core.domain.CoreRepository
import com.farasatnovruzov.spendingtracker.core.domain.LocalSpendingDataSource
import com.farasatnovruzov.spendingtracker.core.domain.Spending
import com.farasatnovruzov.spendingtracker.spending_overview.presentation.util.randomColor
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class SpendingOverviewViewModel(
    private val spendingDataSource: LocalSpendingDataSource,
    private val coreRepository: CoreRepository
): ViewModel() {

    var state by mutableStateOf(SpendingOverviewState())
        private set

    fun onAction(action: SpendingOverviewAction){
        when(action){
            SpendingOverviewAction.LoadSpendingOverviewBalance->{
                loadSpendingListAndBalance()
            }
            is SpendingOverviewAction.OnDateChange -> TODO()
            is SpendingOverviewAction.OnDeleteSpending -> TODO()
        }
    }

    private fun loadSpendingListAndBalance(){
        viewModelScope.launch {
            val allDates = spendingDataSource.getAllDates()
            state = state.copy(
                spendingList = getSpendingListByDate(
                    allDates.lastOrNull() ?: ZonedDateTime.now()
                ),
                balance = coreRepository.getBalance() - (spendingDataSource.getSpendBalance() ?: 0.0),
                pickedDate = allDates.lastOrNull() ?: ZonedDateTime.now(),
                datesList = allDates.reversed()
            )

            val dummyDates = listOf(
                ZonedDateTime.parse("2026-09-01T10:15:30+01:00"),
                ZonedDateTime.parse("2026-09-01T10:15:30+01:00"),
                ZonedDateTime.parse("2026-09-01T10:15:30+01:00"),
                ZonedDateTime.parse("2026-09-01T10:15:30+01:00"),
                ZonedDateTime.parse("2026-09-01T10:15:30+01:00"),
                ZonedDateTime.parse("2026-09-01T10:15:30+01:00"),
                ZonedDateTime.parse("2026-09-01T10:15:30+01:00"),
                ZonedDateTime.parse("2026-09-01T10:15:30+01:00"),
                ZonedDateTime.parse("2026-09-01T10:15:30+01:00"),
                ZonedDateTime.parse("2026-09-01T10:15:30+01:00"),
                ZonedDateTime.parse("2026-09-01T10:15:30+01:00"),
                ZonedDateTime.parse("2026-09-01T10:15:30+01:00"),
                ZonedDateTime.parse("2026-09-01T10:15:30+01:00"),
                ZonedDateTime.parse("2026-09-01T10:15:30+01:00"),


            )

            state = state.copy(
                datesList = dummyDates
            )
        }
    }

    private suspend fun getSpendingListByDate(date: ZonedDateTime): List<Spending>{
        return spendingDataSource
            .getAllSpendingsByDate(date)
            .reversed()
            .map {
                it.copy(color = randomColor())
            }
    }



}