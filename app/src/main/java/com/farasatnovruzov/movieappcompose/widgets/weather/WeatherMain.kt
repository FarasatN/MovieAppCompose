package com.farasatnovruzov.movieappcompose.widgets.weather

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
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
import com.farasatnovruzov.movieappcompose.utils.fahrenheitToCelsius
import com.farasatnovruzov.movieappcompose.utils.formatDate
import kotlin.math.max
import kotlin.math.min


@Composable
fun SunsetSunRiseRow(data: Weather) {
    val today = data.list[0]
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, bottom = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                imageVector = Icons.Default.WbSunny,
                contentDescription = "Sunrise Icon",
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = formatDate(today.sunrise.toLong()),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(2.dp)
            )
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                imageVector = Icons.Default.WbTwilight,
                contentDescription = "Sunset Icon",
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = formatDate(today.sunset.toLong()),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(2.dp)
            )
        }

    }

    Text(
        "Today",
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize(),
//            .background(bgColor),
//            .background(Color.Magenta),
//        color = Color.Magenta,
        shape = RoundedCornerShape(14.dp),
//        shadowElevation = 4.dp
    ) {

        LazyColumn(
            modifier = Modifier.padding(2.dp),
//                .background(Color.Magenta),
//                .shadow(4.dp),
            contentPadding = PaddingValues(1.dp),
        ) {
            itemsIndexed(items = data.list) { index, item ->
                println("item: ${index}/  ${formatDate(item.dt.toLong())}")
                WeatherDetailRow(weatherItem = item)

            }
        }
    }
}

enum class WeatherType {
    Clear, Rain, Snow, Clouds, Thunderstorm, Drizzle, Fogg, Unknown
}

@Composable
fun WeatherDetailRow(weatherItem: WeatherItem) {
    val animatedBrush = GetWeatherAnimatedBrush(weatherItem)
// Determine the weather type to pass to the particle system
    val weatherType = when (weatherItem.weather[0].main) {
        "Clear" -> WeatherType.Clear
        "Clouds" -> WeatherType.Clouds
        "Rain" -> WeatherType.Rain
        "Snow" -> WeatherType.Snow
        "Thunderstorm" -> WeatherType.Thunderstorm
        "Drizzle" -> WeatherType.Drizzle
        "Fog" -> WeatherType.Fogg
        else -> WeatherType.Unknown
    }
    Surface(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        color = Color.Transparent,
        shadowElevation = 1.dp
    ) {

        Box(
            modifier = Modifier
//                .fillMaxWidth()
                .fillMaxSize()
                .background(animatedBrush)
        )
        {
//            for (i in 0 until 20) {
//                Raindrop(
//                    modifier = Modifier
//                        .offset(
//                            x = (i * 50).dp,
//                            y = (i * 10).dp
//                        )
////                    .size(4.dp, 100.dp)
//                    .fillMaxSize()
//                        .rotate(15f) // Angle for rainfall
//                )
//            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Text(
//                    text = formatDate(weatherItem.dt.toLong()).split(",")[0],
                    text = formatDate(weatherItem.dt.toLong()),
                    modifier = Modifier.padding(start = 5.dp)
                )
                WeatherStateImage(
                    todayImageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png",
                    size = 70.dp
                )
                Surface(
                    modifier = Modifier.padding(0.dp),
                    shape = CircleShape,
                    color = SkyBlue
                ) {
                    Text(
                        text = weatherItem.weather[0].description,
                        modifier = Modifier.padding(4.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Text(text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Red.copy(alpha = 0.7f),
                            fontWeight = FontWeight.SemiBold
                        )
                    ) {
                        append(fahrenheitToCelsius(weatherItem.temp.day) + "°")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color.DarkGray.copy(alpha = 0.3f),
                            fontWeight = FontWeight.SemiBold
                        )
                    ) {
                        append(fahrenheitToCelsius(weatherItem.temp.night) + "°")
                    }
                })
            }
        }
    }
}


@Composable
fun WeatherStateImage(todayImageUrl: String, size: Dp = 120.dp) {
    Image(
        painter = rememberAsyncImagePainter(todayImageUrl),
        contentDescription = "icon image",
//        colorFilter = ColorFilter.tint(Color.Transparent),
        modifier = Modifier
            .size(size)
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
fun HumidityWindPressureRow(weather: WeatherItem) {
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
                text = "${weather.humidity}%",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                imageVector = Icons.Default.Compress,
                contentDescription = "Pressure Icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${weather.pressure} psi",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                imageVector = Icons.Default.WindPower,
                contentDescription = "Wind Icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${weather.speed} mph",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


//=========================================================================================
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
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
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
        colors = gradientColors,
        startX = -1000f + offset,
        endX = offset
    )
}


//############################################################################################
@Composable
fun Raindrop(modifier: Modifier = Modifier) {
    // Infinite animation for continuous movement
    val infiniteTransition = rememberInfiniteTransition()
    val animateState by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1000, easing = LinearEasing),
            RepeatMode.Restart
        )
    )

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val centerX = size.width / 2

        // Calculate positions based on animation state
        val scopeHeight = height - width / 2
        val space = height / 2.2f + width / 2
        val spacePos = scopeHeight * animateState
        val sy1 = spacePos - space / 2
        val sy2 = spacePos + space / 2
        val lineHeight = scopeHeight - space

        // Draw first line segment
        drawLine(
            color = Color.White,
            start = Offset(centerX, max(0f, sy1 - lineHeight)),
            end = Offset(centerX, max(0f, sy1)),
            strokeWidth = width,
            cap = StrokeCap.Round
        )

        // Draw second line segment
        drawLine(
            color = Color.White,
            start = Offset(centerX, min(sy2, scopeHeight)),
            end = Offset(centerX, min(sy2 + lineHeight, scopeHeight)),
            strokeWidth = width,
            cap = StrokeCap.Round
        )
    }
}
