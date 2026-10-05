package com.farasatnovruzov.spendingtracker.spending_details.presentation

sealed interface SpendingDetailsAction {

    data class UpdateName(val newName: String): SpendingDetailsAction
    data class UpdatePrice(val newPrice: String): SpendingDetailsAction
    data class UpdateKilograms(val newKilograms: String): SpendingDetailsAction
    data class UpdateQuantity(val newQuantity: String): SpendingDetailsAction
    data object SaveSpending: SpendingDetailsAction
}
