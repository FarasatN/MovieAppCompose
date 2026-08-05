package com.farasatnovruzov.movieappcompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun CardSection(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .height(100.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            androidx.compose.ui.graphics.Color.Blue,
                            androidx.compose.ui.graphics.Color.Green
                        )
                    )
                )
        )
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .padding(horizontal = 22.dp, vertical = 10.dp),
            elevation = CardDefaults.cardElevation(6.dp),
            shape = RoundedCornerShape(20.dp)

        ) {
            CardContent(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun CardContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    listOf(
                        MaterialTheme.colorScheme.secondary,
                        MaterialTheme.colorScheme.error
                    )
                )
            )
    ) {
        Icon(
            imageVector = androidx.compose.material.icons.Icons.Rounded.Star,
            contentDescription = null,
            tint = Color.LightGray.copy(0.2f),
            modifier = Modifier
                .fillMaxSize()
                .offset(110.dp, 40.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp)
                .align(Alignment.TopCenter),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Balance",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary.copy(0.6f),
                fontFamily = Font(R.font.play_bold).toFontFamily(),
                fontSize = 22.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$120",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                fontFamily = Font(R.font.play_bold).toFontFamily(),
                fontSize = 40.sp
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp)
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "**** 3478",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary.copy(0.8f),
                fontFamily = Font(R.font.play_bold).toFontFamily(),
                fontSize = 18.sp)
            Icon(
                painter = painterResource(R.drawable.visa),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.background.copy(0.7f),
            )

        }
    }
}