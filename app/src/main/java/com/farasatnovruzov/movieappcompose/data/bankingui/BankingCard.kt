package com.farasatnovruzov.movieappcompose.data.bankingui

import androidx.compose.ui.graphics.Brush

data class BankingCard(
    val cardType: String,
    val cardTNumber: String,
    val cardName: String,
    val balance: Double,
    val color: Brush

)