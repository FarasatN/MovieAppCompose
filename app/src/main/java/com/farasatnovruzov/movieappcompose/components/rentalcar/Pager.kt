package com.farasatnovruzov.movieappcompose.components.rentalcar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true, showSystemUi = true,
    backgroundColor = 0xFF1E1E1E
    )
@Composable
fun Pager(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
//        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Luxurious \nRental Cars",
            fontSize = 25.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            lineHeight = 40.sp,
            modifier = Modifier.padding(horizontal = 22.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.padding(bottom = 16.dp),
        ){
            Text(
                text = "Luxurious ",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            Row(modifier = Modifier.weight(1f),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "VIP Cars",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
//                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "New ",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier
                        .alpha(.7f)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.Yellow)
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                )
            }
        }
//        Spacer(modifier = Modifier.height(10.dp).background(Color.Transparent))
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = 3.dp,
//                color = MaterialTheme.colorScheme.onBackground
                color = Color.Yellow
            )
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = 1.dp,
//                color = MaterialTheme.colorScheme.onBackground
                color = Color.Yellow
            )

        }
    }


}