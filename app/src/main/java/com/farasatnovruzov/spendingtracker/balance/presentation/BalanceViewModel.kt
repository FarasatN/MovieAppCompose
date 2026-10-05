package com.farasatnovruzov.spendingtracker.balance.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farasatnovruzov.spendingtracker.core.domain.CoreRepository
import com.farasatnovruzov.spendingtracker.core.presentation.util.filterDecimalInput
import com.farasatnovruzov.spendingtracker.core.presentation.util.toAmountOrNull
import com.farasatnovruzov.spendingtracker.core.presentation.util.toInputText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class BalanceViewModel(
    private val coreRepository: CoreRepository
) : ViewModel() {
    var state by mutableStateOf(BalanceState())
        private set

    private val _events = Channel<BalanceEvent>()
    val events = _events.receiveAsFlow()

    init {
        viewModelScope.launch {
            state = state.copy(
                balanceText = coreRepository.getBalance().toInputText()
            )
        }
    }

    fun onAction(action: BalanceAction) {
        when (action) {
            is BalanceAction.OnBalanceChanged -> {
                filterDecimalInput(action.newBalance)?.let {
                    state = state.copy(balanceText = it)
                }
            }

            BalanceAction.OnBalanceSaved -> {
                viewModelScope.launch {
                    val balance = state.balanceText.toAmountOrNull() ?: return@launch
                    coreRepository.updateBalance(balance)   // yazma bitir...
                    _events.send(BalanceEvent.Saved)        // ...və yalnız ondan sonra geri qayıdılır
                }
            }
        }
    }
}
