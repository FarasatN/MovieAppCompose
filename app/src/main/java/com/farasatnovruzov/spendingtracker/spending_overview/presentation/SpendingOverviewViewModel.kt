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
) : ViewModel() {

    var state by mutableStateOf(SpendingOverviewState())
        private set

    fun onAction(action: SpendingOverviewAction) {
        when (action) {
            SpendingOverviewAction.LoadSpendingOverviewBalance -> {
                loadSpendingListAndBalance()
            }

            is SpendingOverviewAction.OnDateChange -> {
                val newDate = state.datesList.getOrNull(action.newDate) ?: return
                viewModelScope.launch {
                    state = state.copy(
                        pickedDate = newDate,
                        spendingList = getSpendingListByDate(newDate)
                    )
                }
            }

            is SpendingOverviewAction.OnDeleteSpending -> {
                viewModelScope.launch {
                    spendingDataSource.deleteSpending(action.spendingId)

                    val updatedDates = spendingDataSource.getAllDates()
                    val updatedSpendings = getSpendingListByDate(state.pickedDate)
                    val updatedBalance = calculateRemainingBalance()

                    state = state.copy(
                        spendingList = updatedSpendings,
                        datesList = updatedDates.reversed(),
                        balance = updatedBalance
                    )
                }
            }
        }
    }

    private fun loadSpendingListAndBalance() {
        viewModelScope.launch {
            val allDates = spendingDataSource.getAllDates()
            val targetDate = allDates.lastOrNull() ?: ZonedDateTime.now()

            state = state.copy(
                spendingList = getSpendingListByDate(targetDate),
                balance = calculateRemainingBalance(),
                pickedDate = targetDate,
                datesList = allDates.reversed()
            )
        }
    }

    private suspend fun calculateRemainingBalance(): Double {
        val currentBalance = coreRepository.getBalance()
        val totalSpent = spendingDataSource.getSpendBalance() ?: 0.0
        return currentBalance - totalSpent
    }

    private suspend fun getSpendingListByDate(date: ZonedDateTime): List<Spending> {
        return spendingDataSource
            .getAllSpendingsByDate(date)
            .reversed()
            .map { spending ->
                spending.copy(color = randomColor())
            }
    }
}