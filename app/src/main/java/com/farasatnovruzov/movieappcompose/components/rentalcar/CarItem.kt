package com.farasatnovruzov.movieappcompose.components.rentalcar

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farasatnovruzov.movieappcompose.data.rentalcar.Car
import com.farasatnovruzov.movieappcompose.data.rentalcar.luxuriousCars

@Composable
fun CarItem(modifier: Modifier = Modifier, car: Car) {
    Box(
        modifier = modifier // Xaricdən gələn parametrik modifier
            .fillMaxWidth()  // Standart ehtiyac olan ölçülər
            .height(230.dp)
            .padding(10.dp)
            .clip(RoundedCornerShape(40.dp))
            .background(car.bgColor)
    ) {
        Image(
            painter = painterResource(id = car.image),
            contentDescription = car.name,
            modifier = Modifier.offset(x = 160.dp, y = 0.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                CarInfo(car = car)
                Spacer(modifier = Modifier.height(20.dp))
                Rating(car = car)
            }
            BuyButton(car = car)
        }
    }
}

@Composable
fun BuyButton(modifier: Modifier= Modifier, car: Car) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(40.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(vertical = 8.dp)
            .padding(start = 25.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column{
            Text(
                text = "$${car.rentalDays} Days",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(0.8f)
            )
            Text(
                text = "$${car.price}.00",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground.copy(0.7f),
            modifier = Modifier.size(30.dp)
        )


    }

}


@Composable
fun Rating(modifier: Modifier = Modifier, car: Car) {
    Column(modifier = modifier.padding(start = 20.dp)) {
        Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Box{
                Rater(image = car.recommenders[0])
                Rater(image = car.recommenders[1],modifier = Modifier.padding(start = 20.dp))
                Rater(image = car.recommenders[1],modifier = Modifier.padding(start = 40.dp))
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "(${car.recommendationRate})",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Text(
            text = "${car.recommendation}% Recommended",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun Rater(modifier: Modifier = Modifier, image: Int) {
    Image(
        painter = painterResource(id = image),
        contentDescription = null,
        modifier = modifier
            .size(30.dp)
            .clip(CircleShape)
            .border(
                color = MaterialTheme.colorScheme.outline, // Temaya uyğun kontur rəngi
                width = 2.dp,
                shape = CircleShape
            ).background(MaterialTheme.colorScheme.surfaceVariant)
    )

}


@Composable
fun CarInfo(modifier: Modifier = Modifier, car: Car) {
    Row(
        modifier = modifier.padding(top = 20.dp, start = 20.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = car.logo),
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(100.dp))
                .border(
                    color = MaterialTheme.colorScheme.outline, // Temaya uyğun kontur rəngi
                    width = 2.dp,
                    shape = CircleShape
                ).background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(6.dp)
                .size(35.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Row(
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Text(
                    text = "Color: ",
                    fontSize = 12.sp,
                    color = Color.Black.copy(0.8f)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(car.color)
                        .border(
                            color = Color.Black,
                            width = 1.dp,
                            shape = CircleShape
                        )
                )
            }
            Text(
                text = car.name,
                fontSize = 12.sp,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun CarItemPreview() {
    CarItem(
        Modifier
            .fillMaxWidth()
            .height(230.dp),
        car = luxuriousCars[0]
    )

}