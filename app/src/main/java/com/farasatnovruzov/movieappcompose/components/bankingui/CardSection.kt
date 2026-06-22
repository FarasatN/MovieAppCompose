package com.farasatnovruzov.movieappcompose.components.bankingui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farasatnovruzov.movieappcompose.R
import com.farasatnovruzov.movieappcompose.data.bankingui.BankingCard


val cards = listOf(
    BankingCard(
        cardType = "Master Card",
        cardName = "Business",
        cardTNumber = "**** **** **** 1234",
        balance = 2385.22,
        color = getGradient(Color.Cyan, Color.Red)
    ), BankingCard(
        cardType = "Visa",
        cardName = "Personal",
        cardTNumber = "**** **** **** 1235",
        balance = 0.73,
        color = getGradient(Color.Cyan, Color.Green)
    ),

    BankingCard(
        cardType = "Visa",
        cardName = "Savings",
        cardTNumber = "**** **** **** 1236",
        balance = 385.11,
        color = getGradient(Color.Cyan, Color.White)

    )
)

fun getGradient(
    startColor: androidx.compose.ui.graphics.Color, endColor: androidx.compose.ui.graphics.Color
): Brush {
    return Brush.verticalGradient(
        colors = listOf(
            startColor, endColor
        )
    )
}


@Preview(showBackground = true)
@Composable
fun CardSection() {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items(cards.size) { index ->
            CardItem(index)
        }
    }
}

@Composable
fun CardItem(
    index: Int,
) {
    val card = cards[index]
    var lastItemPaddingEnd = 0.dp
    if (index == cards.size - 1) {
        lastItemPaddingEnd = 16.dp
    }
    var image = painterResource(R.drawable.mastercard)
    if (card.cardType == "Visa") {
        image = painterResource(R.drawable.visa)
    }

    Box(
        modifier = Modifier.padding(start = 16.dp, end = lastItemPaddingEnd)
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(25.dp))
                .background(card.color)
                .width(250.dp)
                .height(160.dp)
                .clickable {}
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
//            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = image,
                contentDescription = card.cardName,
                modifier = Modifier.width(60.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = card.cardName,
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "$ ${card.balance}",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = card.cardTNumber,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

        }
    }

}
