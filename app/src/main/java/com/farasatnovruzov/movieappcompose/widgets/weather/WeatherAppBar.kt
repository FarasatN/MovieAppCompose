package com.farasatnovruzov.movieappcompose.widgets.weather

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


//@Preview(
//    showBackground = true, showSystemUi = true
//)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(
    title: String = "Baku",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    padding: Dp = 0.dp,
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {},
//    navController: NavHostController
) {
    val roundedShape = RoundedCornerShape(topStartPercent = 0, topEndPercent = 0, bottomEndPercent = 40, bottomStartPercent = 40)
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding)
//            .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
            .shadow(elevation = elevation, shape = roundedShape) // Apply shadow first
            .border(
                width = 0.dp,
//                color = MaterialTheme.colorScheme.secondary,
                color = Color.Transparent,
                shape = roundedShape
            ) // Apply border
            .clip(roundedShape), // Clip the content
//        color = Color.Transparent // Set the background color here
    ) {
        CenterAlignedTopAppBar(
            title = {
//            Text(text = stringResource(R.string.weather))
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.secondary,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    fontSize = 15.sp
                )
            },
            actions = {
                if (isMainScreen) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More Icon",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                } else Box {}
            },
            navigationIcon = {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.clickable {
                            onButtonClicked.invoke()
                        }
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
//            titleContentColor = Color.Black.copy(alpha = 0.87f),
//            navigationIconContentColor = Color.Black.copy(alpha = 0.87f),
//            actionIconContentColor = Color.Black.copy(alpha = 0.87f)
            ),
        )
    }


}