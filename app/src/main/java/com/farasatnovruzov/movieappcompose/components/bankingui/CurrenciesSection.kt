package com.farasatnovruzov.movieappcompose.components.bankingui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.CurrencyYen
import androidx.compose.material.icons.rounded.Euro
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farasatnovruzov.movieappcompose.data.bankingui.Currency
import com.farasatnovruzov.movieappcompose.ui.theme.GreenStart

val currencies = listOf(
    Currency("USD", 23.35f, 23.25f, Icons.Rounded.AttachMoney),
    Currency("EUR", 13.35f, 13.25f, Icons.Rounded.Euro),
    Currency("YEN", 26.35f, 26.35f, Icons.Rounded.CurrencyYen),
    Currency("GBP", 23.35f, 23.25f, Icons.Rounded.AttachMoney),
    Currency("CHF", 63.35f, 73.25f, Icons.Rounded.Euro),
    Currency("CAD", 16.35f, 16.35f, Icons.Rounded.CurrencyYen),
)

@Preview(showBackground = true)
@Composable
fun CurrenciesSection() {
    var isVisible by remember { mutableStateOf(false) }

    // Artıq ikinci bir 'iconState' saxlanmır, birbaşa isVisible-dən götürülür
    val iconVector =
        if (isVisible) Icons.Rounded.KeyboardArrowUp else Icons.Rounded.KeyboardArrowDown

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(MaterialTheme.colorScheme.inverseOnSurface)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            // Header: Yalnız kiçik ikon yox, bütün başlıq hissəsi kliklənə bilən edildi (UX təkmilləşdirilməsi)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isVisible = !isVisible }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondary)
                ) {
                    Icon(
                        modifier = Modifier.size(25.dp),
                        imageVector = iconVector,
                        contentDescription = if (isVisible) "Hide Currencies" else "Show Currencies",
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))

                Text(
                    text = "Currencies",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    fontWeight = FontWeight.Bold
                )
            }

            // Material3-ün standart Divider komponenti
            HorizontalDivider(color = MaterialTheme.colorScheme.secondaryContainer)

            if (isVisible) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(25.dp))
                        .background(MaterialTheme.colorScheme.background)
                        .padding(16.dp)
                ) {
                    // Header Row - BoxWithConstraints ləğv edildi, weight(1f) ilə 3 bərabər sütuna bölündü
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Currency",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Buy",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.End
                        )

                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Sell",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.End
                        )
                    }

                    // LazyColumn: `key` əlavə edilərək recomposition optimallaşdırıldı
                    LazyColumn {
                        items(
                            items = currencies,
                            key = { currency -> "${currency.name}_${currency.buy}_${currency.sell}" }
                        ) { currency ->
                            CurrencyItem(currency = currency)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CurrencyItem(currency: Currency) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 1. Sütun: Icon və Valyuta adı (Genişliyin 1/3-i)
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(GreenStart)
                    .padding(4.dp)
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = currency.icon,
                    contentDescription = currency.name,
                    tint = Color.White
                )
            }

            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = currency.name,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }

        // 2. Sütun: Alış qiyməti (Genişliyin 1/3-i)
        Text(
            modifier = Modifier.weight(1f),
            text = "$ ${currency.buy}",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.End
        )

        // 3. Sütun: Satış qiyməti (Genişliyin 1/3-i)
        Text(
            modifier = Modifier.weight(1f),
            text = "$ ${currency.sell}",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.End
        )
    }
}