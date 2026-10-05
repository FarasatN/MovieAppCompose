package com.farasatnovruzov.spendingtracker.spending_details.domain

import com.farasatnovruzov.spendingtracker.core.domain.LocalSpendingDataSource
import com.farasatnovruzov.spendingtracker.core.domain.Spending

class UpsertSpendingUseCase(
    private val spendingDataSource: LocalSpendingDataSource
) {

    suspend operator fun invoke(spending: Spending): Boolean {
        if (spending.name.isBlank()) {
            return false
        }
        if (spending.price < 0) {
            return false
        }
        if (spending.kilograms < 0) {
            return false
        }
        if (spending.quantity < 0) {
            return false
        }

        // Calculate total price: unit price * quantity * kilograms (if provided)
        val qMultiplier = if (spending.quantity > 0) spending.quantity else 1.0
        val kMultiplier = if (spending.kilograms > 0) spending.kilograms else 1.0

        val totalPrice = spending.price * qMultiplier * kMultiplier

        val spendingToSave = spending.copy(price = totalPrice)

        spendingDataSource.upsertSpending(spendingToSave)
        return true
    }
}
