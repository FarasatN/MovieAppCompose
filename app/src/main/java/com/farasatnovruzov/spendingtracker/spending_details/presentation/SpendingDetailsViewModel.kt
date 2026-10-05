package com.farasatnovruzov.spendingtracker.spending_details.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farasatnovruzov.spendingtracker.core.domain.LocalSpendingDataSource
import com.farasatnovruzov.spendingtracker.core.domain.Spending
import com.farasatnovruzov.spendingtracker.core.presentation.util.filterDecimalInput
import com.farasatnovruzov.spendingtracker.core.presentation.util.toAmountOrNull
import com.farasatnovruzov.spendingtracker.core.presentation.util.toInputText
import com.farasatnovruzov.spendingtracker.spending_details.domain.UpsertSpendingUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.ZoneOffset
import java.time.ZonedDateTime

class SpendingDetailsViewModel(
    private val upsertSpendingUseCase: UpsertSpendingUseCase,
    private val localSpendingDataSource: LocalSpendingDataSource,
) : ViewModel() {

    var state by mutableStateOf(SpendingDetailsState())
        private set

    private val _eventChannel = Channel<SpendingDetailsEvent>()

    val event = _eventChannel.receiveAsFlow()

    // ViewModel ekran fırlananda sağ qalır, bu bayraq da onunla qalır.
    // Beləliklə LaunchedEffect yenidən işləsə belə yazılan mətn silinmir.
    private var hasLoaded = false

    fun loadSpending(spendingId: Int?) {
        if (hasLoaded) return
        hasLoaded = true

        // Yeni xərc: state artıq boşdur, yükləməyə ehtiyac yoxdur
        if (spendingId == null || spendingId == -1) return

        viewModelScope.launch {
            localSpendingDataSource.getSpending(spendingId)?.let { spending ->
                val divisor = when {
                    spending.quantity > 1 -> spending.quantity
                    spending.kilograms > 0 -> spending.kilograms
                    else -> 1.0
                }
                val unitPrice = spending.price / divisor
                state = state.copy(
                    spendingId = spending.spendingId,
                    name = spending.name,
                    price = unitPrice.toInputText(),
                    kilograms = spending.kilograms.toInputText(),
                    quantity = spending.quantity.toInputText(),
                    dateTimeUtc = spending.dateTimeUtc
                )
            }
        }
    }

    fun onAction(action: SpendingDetailsAction) {
        when (action) {
            is SpendingDetailsAction.UpdateName -> {
                state = state.copy(name = action.newName)
            }

            is SpendingDetailsAction.UpdatePrice -> {
                filterDecimalInput(action.newPrice)?.let { state = state.copy(price = it) }
            }

            is SpendingDetailsAction.UpdateKilograms -> {
                filterDecimalInput(action.newKilograms)?.let { state = state.copy(kilograms = it) }
            }

            is SpendingDetailsAction.UpdateQuantity -> {
                filterDecimalInput(action.newQuantity)?.let { state = state.copy(quantity = it) }
            }

            SpendingDetailsAction.SaveSpending -> {
                viewModelScope.launch {
                    if (saveSpending()) {
                        _eventChannel.send(SpendingDetailsEvent.SaveSuccess)
                    } else {
                        _eventChannel.send(SpendingDetailsEvent.SaveFailed)
                    }
                }
            }
        }
    }

    private suspend fun saveSpending(): Boolean {
        // Mətn -> rəqəm: "." kimi yanlış mətn olsa null qayıdır və saxlama uğursuz sayılır
        val price = state.price.toAmountOrNull() ?: return false
        val kilograms = state.kilograms.toAmountOrNull() ?: return false
        val quantity = state.quantity.toAmountOrNull() ?: return false

        val spending = Spending(
            spendingId = state.spendingId,
            name = state.name,
            price = price,
            kilograms = kilograms,
            quantity = quantity,
            dateTimeUtc = state.dateTimeUtc ?: ZonedDateTime.now(ZoneOffset.UTC)
        )
        return upsertSpendingUseCase(spending)
    }
}
