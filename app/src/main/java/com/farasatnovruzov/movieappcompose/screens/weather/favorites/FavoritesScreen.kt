package com.farasatnovruzov.movieappcompose.screens.weather.favorites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.farasatnovruzov.movieappcompose.model.weather.local.Favorite
import com.farasatnovruzov.movieappcompose.navigation.weather.WeatherScreens
import com.farasatnovruzov.movieappcompose.ui.theme.SkyBlue
import com.farasatnovruzov.movieappcompose.widgets.weather.WeatherAppBar

@Composable
fun FavoritesScreen(
    navController: NavController, favoriteViewModel: FavoriteViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            WeatherAppBar(
                title = "Favorite Cities",
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                isMainScreen = false,
                navController = navController,
                onButtonClicked = {
                    navController.popBackStack()
                })
        }) { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val list = favoriteViewModel.favList.collectAsState().value
                LazyColumn {
                    items(items = list) {
                        CityRow(it, navController = navController, favoriteViewModel)
                    }
                }

            }
        }

    }
}

@Composable
fun CityRow(
    favorite: Favorite, navController: NavController, favoriteViewModel: FavoriteViewModel
) {
    Surface(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                navController.navigate(WeatherScreens.MainScreen.name + "/${favorite.city}")
            }, shape = CircleShape.copy(topEnd = CornerSize(6.dp)), color = SkyBlue
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = favorite.city, modifier = Modifier.padding(4.dp))
            Surface(
                modifier = Modifier.padding(0.dp), shape = CircleShape, color = Color.White
            ) {
                Text(
                    text = favorite.country,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = "delete",
                modifier = Modifier.clickable {
                    favoriteViewModel.deleteFavorite(favorite)
                },
                tint = Color.Red.copy(alpha = .7f)
            )
        }
    }

}