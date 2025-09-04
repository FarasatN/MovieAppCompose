package com.farasatnovruzov.movieappcompose.widgets.weather

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.farasatnovruzov.movieappcompose.model.weather.local.Favorite
import com.farasatnovruzov.movieappcompose.navigation.weather.WeatherScreens
import com.farasatnovruzov.movieappcompose.screens.weather.favorites.FavoriteViewModel
import com.farasatnovruzov.movieappcompose.ui.theme.SkyBlue


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
    navController: NavController?,
    favoriteViewModel: FavoriteViewModel = hiltViewModel()
) {
    val showDialog = rememberSaveable { mutableStateOf(false) }
    val showIt = rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    if (showDialog.value) {
        SettingsDropDownMenu2(showDialog = showDialog, navController = navController)
    }
    val roundedShape = RoundedCornerShape(
        topStartPercent = 0,
        topEndPercent = 0,
        bottomEndPercent = 40,
        bottomStartPercent = 40
    )
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
                    IconButton(onClick = {
                        onAddActionClicked.invoke()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                    IconButton(onClick = {
                        showDialog.value = true
                    }) {
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
                if (isMainScreen) {
                    val dataList = title.split(",")
                    val isAlreadyFavList = favoriteViewModel
                        .favList.collectAsState().value.filter { item ->
                            (item.city == dataList[0])
                        }
                    if (isAlreadyFavList.isNullOrEmpty()) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite Icon",
                            modifier = Modifier
                                .padding(start = 10.dp)
//                            .scale(0.9f)
                                .clickable {
                                    favoriteViewModel.insertFavorite(
                                        Favorite(
                                            city = dataList[0],
                                            country = dataList[1],
                                        )
                                    ).run {
                                        showIt.value = true
                                    }
                                    ShowToast(context = context, showIt = showIt)
                                },
                        )
                    } else {
                        showIt.value = false
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorite Icon",
                            tint = Color.Red.copy(alpha = 0.7f),
                            modifier = Modifier.padding(start = 10.dp)
//                            .scale(0.9f)
                        )
                    }
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

fun ShowToast(context: Context, showIt: MutableState<Boolean>) {
    if (showIt.value) {
        Toast.makeText(context, "City added to Favorites", Toast.LENGTH_SHORT).show()
        showIt.value = false
    }
}

@Composable
fun SettingsDropDownMenu2(showDialog: MutableState<Boolean>, navController: NavController?) {
    var expanded by rememberSaveable { mutableStateOf(true) }
    val items = listOf("About", "Favorites", "Settings")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 10.dp)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false; showDialog.value = false },
            modifier = Modifier
                .fillMaxWidth(.45f)
//                .background(Color.White)
        ) {
            items.forEachIndexed { index, text ->
                DropdownMenuItem(
                    text = {
                        // Use a Row to align the icon and text horizontally
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                imageVector = when (text) {
                                    "About" -> Icons.Default.Info
                                    "Favorites" -> Icons.Default.FavoriteBorder
                                    else -> Icons.Default.Settings
                                },
                                contentDescription = null,
                                tint = SkyBlue
                            )
                            Spacer(modifier = Modifier.width(8.dp)) // Add a spacer for separation
                            Text(
                                text = text,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    },
                    onClick = {
                        expanded = false
                        showDialog.value = false
                        Log.d("Dropdown", "DropDownMenu clicked: $text")
                        navController?.navigate(
                            when (text) {
                                "About" -> WeatherScreens.AboutScreen.name
                                "Favorites" -> WeatherScreens.FavoritesScreen.name
                                else -> WeatherScreens.SettingsScreen.name
                            }
                        )
                    },
                )
            }
        }
    }
}

