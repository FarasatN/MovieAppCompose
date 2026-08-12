package com.farasatnovruzov.movieappcompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.DirectionsCar
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.material.icons.rounded.Flight
import androidx.compose.material.icons.rounded.LocalHospital
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Restaurant
import androidx.compose.material.icons.rounded.School
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material.icons.rounded.Subscriptions
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

// 1. Şrifti global obyekt kimi 1 dəfə yaradırıq (Optimizasiya)
val PlayFontFamily = FontFamily(Font(R.font.play_regular))

@Composable
fun SpendingSection(modifier: Modifier = Modifier) {
    // Top-level Column olmalıdır ki, Text və LazyRow üst-üstə düşməsin
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Spending Breakdown",
            fontFamily = PlayFontFamily,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        SpendingList()
    }
}

@Composable
fun SpendingList(modifier: Modifier = Modifier) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp) // Kartlar arası məsafə
    ) {
        // key = { it.name } Compose-a recomposition optimizasiyası verir
        items(
            items = spendingItems,
            key = { item -> item.name }
        ) { item ->
            SpendingItemCard(spendingItem = item)
        }
    }
}

@Composable
fun SpendingItemCard(
    spendingItem: SpendingItem,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier.size(150.dp),
        shape = RoundedCornerShape(20.dp),
        // Arxa fonu birbaşa Card-a veririk (Lüzumsuz Modifier.background-ları təmizləyirik)
//        colors = CardDefaults.elevatedCardColors(
////            containerColor = spendingItem.color.copy(alpha = 0.15f)
////            containerColor = Color.White
//        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .background(spendingItem.color.copy(0.5f))
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Icon(
                imageVector = spendingItem.icon,
                contentDescription = spendingItem.name,
                tint = Color.Black.copy(alpha = 0.8f),
                modifier = Modifier.size(36.dp)
            )

            Column {
                Text(
                    text = spendingItem.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "$${spendingItem.amount.toInt()}", // Təmiz formatlama
                    fontSize = 18.sp,
                    fontFamily = PlayFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black.copy(alpha = 0.85f)
                )
            }
        }
    }
}

data class SpendingItem(
    val name: String,
    val amount: Float,
    val color: Color,
    val icon: ImageVector
)

fun randomColor(): Color {
    return Color(
        red = Random.nextInt(50, 220),
        green = Random.nextInt(50, 220),
        blue = Random.nextInt(50, 220)
    )
}

val spendingItems = listOf(
    SpendingItem("Food", 200f, randomColor(), Icons.Rounded.Restaurant),
    SpendingItem("Shopping", 150f, randomColor(), Icons.Rounded.ShoppingCart),
    SpendingItem("Transport", 80f, randomColor(), Icons.Rounded.DirectionsCar),
    SpendingItem("Entertainment", 60f, randomColor(), Icons.Rounded.Movie),
    SpendingItem("Subscriptions", 25f, randomColor(), Icons.Rounded.Subscriptions),
    SpendingItem("Health & Medical", 95f, randomColor(), Icons.Rounded.LocalHospital),
    SpendingItem("Gym & Fitness", 50f, randomColor(), Icons.Rounded.FitnessCenter),
    SpendingItem("Travel", 450f, randomColor(), Icons.Rounded.Flight),
    SpendingItem("Education", 110f, randomColor(), Icons.Rounded.School),
    SpendingItem("Investments", 300f, randomColor(), Icons.Rounded.AccountBalanceWallet)
)