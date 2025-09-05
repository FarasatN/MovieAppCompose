package com.farasatnovruzov.movieappcompose.widgets.weather

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Compress
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WbTwilight
import androidx.compose.material.icons.filled.WindPower
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.farasatnovruzov.movieappcompose.model.weather.remote.Weather
import com.farasatnovruzov.movieappcompose.model.weather.remote.WeatherItem
import com.farasatnovruzov.movieappcompose.ui.theme.SkyBlue
import com.farasatnovruzov.movieappcompose.utils.formatDate


@Composable
fun SunsetSunRiseRow(data: Weather) {
    val today = data.list[0]
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = Icons.Default.WbSunny,
                contentDescription = "Sunrise Icon",
                modifier = Modifier
                    .size(28.dp)
                    .padding(1.dp)
            )
            Text(
                text = formatDate(today.sunrise.toLong()),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 5.dp)
            )
        }
        Row(
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = Icons.Default.WbTwilight,
                contentDescription = "Sunset Icon",
                modifier = Modifier
                    .size(28.dp)
                    .padding(1.dp)
            )
            Text(
                text = formatDate(today.sunset.toLong()),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 5.dp)
            )
        }

    }

    Text(
        "This Week", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize(),
        shape = RoundedCornerShape(14.dp),
    ) {

        LazyColumn(
            modifier = Modifier.padding(2.dp),
            contentPadding = PaddingValues(1.dp),
        ) {
            itemsIndexed(items = data.list) { index, item ->
                println("item: ${index}/  ${formatDate(item.dt.toLong())}")
                WeatherDetailRow(weatherItem = item)

            }
        }
    }
}


@Composable
fun WeatherDetailRow(weatherItem: WeatherItem) {
    val animatedBrush = GetWeatherAnimatedBrush(weatherItem)
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        color = Color.Transparent,
        shadowElevation = 1.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
//                .background(animatedBrush)
                .drawWithCache {
                    onDrawBehind {
                        drawRect(animatedBrush)
                    }
                }) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = formatDate(weatherItem.dt.toLong()).split(",")[0],
                    modifier = Modifier.padding(start = 12.dp)
                )
                WeatherStateImage(
                    todayImageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png",
                    size = 60.dp,
                    padding = 5.dp
                )
                Surface(
                    modifier = Modifier.padding(0.dp), shape = CircleShape, color = SkyBlue
                ) {
                    Text(
                        text = weatherItem.weather[0].description,
                        modifier = Modifier.padding(4.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Text(
                    modifier = Modifier.padding(
                        start = 5.dp, top = 5.dp, bottom = 5.dp, end = 12.dp
                    ), text = buildAnnotatedString {

                        withStyle(
                            style = SpanStyle(
                                color = Color.Red.copy(alpha = 0.7f),
                                fontWeight = FontWeight.SemiBold
                            )
                        ) {
                            append(weatherItem.temp.day.toInt().toString())
                        }
                        append("\n")
                        withStyle(
                            style = SpanStyle(
                                color = Color.DarkGray.copy(alpha = 0.3f),
                                fontWeight = FontWeight.SemiBold
                            )
                        ) {
                            append(weatherItem.temp.night.toInt().toString())
                        }
                    })
            }
        }
    }
}

@Composable
fun WeatherStateImage(todayImageUrl: String, size: Dp = 100.dp, padding: Dp = 5.dp) {
    Image(
        painter = rememberAsyncImagePainter(todayImageUrl), contentDescription = "icon image",
//        colorFilter = ColorFilter.tint(Color.Transparent),
        modifier = Modifier
            .size(size)
            .padding(padding)
//            .shadow(elevation = 5.dp, CircleShape) // Apply shadow first
//            .border(
//                width = 1.dp,
//                    color = SkyBlue,
////                color = Color.Transparent,
//                shape = CircleShape
//            ) // Apply border
//            .clip(CircleShape),
    )
}

@Composable
fun HumidityWindPressureRow(weather: WeatherItem, isMetric: Boolean) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                imageVector = Icons.Default.WaterDrop,
                contentDescription = "Humidity Icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${weather.humidity}%", style = MaterialTheme.typography.bodyMedium
            )
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                imageVector = Icons.Default.Compress,
                contentDescription = "Pressure Icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${weather.pressure} psi", style = MaterialTheme.typography.bodyMedium
            )
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                imageVector = Icons.Default.WindPower,
                contentDescription = "Wind Icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${weather.speed} " + if (isMetric) "m/s" else "mph",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


//=========================================================================================
enum class WeatherType {
    Clear, Rain, Snow, Clouds, Thunderstorm, Drizzle, Fogg, Unknown
}

fun getGradientColors(targetState: WeatherType): List<Color> {
    return when (targetState) {
        WeatherType.Clear -> listOf(
            Color(0xFFFFF9C4), // light soft yellow
            Color(0xFFFFF176), // warm mid yellow
            Color(0xFFFBC02D)  // deeper sun
        )

        WeatherType.Rain -> listOf(
            Color(0xFFB3CDE0), // soft rain blue
            Color(0xFF90AFC5), // mid rain blue-gray
            Color(0xff6b87a1)  // shadow rain
        )

        WeatherType.Clouds -> listOf(
            Color(0xFFE0E0E0), // light cloud white
            Color(0xFFB0BEC5), // mid gray
            Color(0xFF78909C)  // darker cloud shadow
        )

        WeatherType.Snow -> listOf(
            Color(0xFFE6F7FF), // soft icy blue
            Color(0xFFB3E5FC), // mid frost
            Color(0xFF81D4FA)  // deeper snow shadow
        )

        WeatherType.Thunderstorm -> listOf(
            Color(0xFF9575CD), // lighter violet
            Color(0xFF673AB7), // deep storm purple
            Color(0xFF311B92)  // dark thunder shadow
        )

        WeatherType.Drizzle -> listOf(
            Color(0xFFB0BEC5), // soft drizzle gray
            Color(0xFF90A4AE), // mid gray-blue
            Color(0xFF607D8B)  // deeper drizzle tone
        )

        else -> listOf(
            Color(0xFF81D4FA), // sky blue
            Color(0xFF4FC3F7), // mid sky
            Color(0xFF0288D1)  // deeper horizon
        )
    }
}

@Composable
fun GetWeatherAnimatedBrush(weatherItem: WeatherItem): Brush {
    val targetState = when (weatherItem.weather[0].main) {
        "Clear" -> WeatherType.Clear
        "Clouds" -> WeatherType.Clouds
        "Rain" -> WeatherType.Rain
        "Snow" -> WeatherType.Snow
        "Thunderstorm" -> WeatherType.Thunderstorm
        "Drizzle" -> WeatherType.Drizzle
        "Fog" -> WeatherType.Fogg
        else -> WeatherType.Unknown
    }
    val gradientColors = getGradientColors(targetState)
    val infiniteTransition = rememberInfiniteTransition(label = "gradientTransition")
    val animatedProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
//        initialValue = -1f,
        targetValue = 1f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 15000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "animatedProgress"
    )
//    return Brush.horizontalGradient(
//        colors = gradientColors,
//        startX = animatedProgress * 500f,
//        endX = (1 - animatedProgress) * 500f
//    )

////     Move the gradient more gradually across a larger space
//    val offset = animatedProgress * 500f
//    return Brush.horizontalGradient(
//        colors = gradientColors,
//        startX = -offset,
//        endX = offset
//    )

    val offset = animatedProgress * 2000f // much wider span
    return Brush.horizontalGradient(
        colors = gradientColors, startX = -1000f + offset, endX = offset
    )
}

