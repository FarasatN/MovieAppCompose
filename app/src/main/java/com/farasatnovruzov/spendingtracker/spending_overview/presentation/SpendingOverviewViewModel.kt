package com.farasatnovruzov.spendingtracker.spending_overview.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farasatnovruzov.spendingtracker.core.domain.CoreRepository
import com.farasatnovruzov.spendingtracker.core.domain.LocalSpendingDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId

class SpendingOverviewViewModel(
    private val spendingDataSource: LocalSpendingDataSource,
    private val coreRepository: CoreRepository
) : ViewModel() {

    var state by mutableStateOf(SpendingOverviewState())
        private set

    // İstifadəçinin menyudan seçdiyi gün (null = hələ seçməyib)
    private val userPickedDate = MutableStateFlow<LocalDate?>(null)

    init {
        observeOverview()
    }

    fun onAction(action: SpendingOverviewAction) {
        when (action) {
            is SpendingOverviewAction.OnDateChange -> {
                state.datesList.getOrNull(action.newDate)?.let { userPickedDate.value = it }
            }

            is SpendingOverviewAction.OnDeleteSpending -> {
                // Yeniləməni özümüz etmirik: Room Flow-u dəyişikliyi görüb yeni state göndərəcək
                viewModelScope.launch {
                    spendingDataSource.deleteSpending(action.spendingId)
                }
            }
        }
    }

    private fun observeOverview() {
        combine(
            spendingDataSource.observeAllSpendings(),
            spendingDataSource.observeTotalSpent(),
            coreRepository.observeBalance(),
            userPickedDate
        ) { allSpendings, totalSpent, budget, pickedDate ->
            // Günləri UTC-yə görə yox, cihazın yerli qurşağına görə qruplaşdırırıq
            val zone = ZoneId.systemDefault()
            val spendingsByDate = allSpendings.groupBy {
                it.dateTimeUtc.withZoneSameInstant(zone).toLocalDate()
            }
            val dates = spendingsByDate.keys.sortedDescending()
            val date = pickedDate?.takeIf { it in spendingsByDate }
                ?: dates.firstOrNull()
                ?: LocalDate.now()

            SpendingOverviewState(
                spendingList = spendingsByDate[date].orEmpty(),
                datesList = dates,
                balance = budget - totalSpent,
                pickedDate = date
            )
        }
            .onEach { newState -> state = newState }
            .launchIn(viewModelScope)
    }
}
