package com.farasatnovruzov.movieappcompose.screens.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.farasatnovruzov.movieappcompose.MovieRow
import com.farasatnovruzov.movieappcompose.navigation.MovieScreens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ), title = {
                    Text(
                        "Movies", maxLines = 1, overflow = TextOverflow.Ellipsis
                    )
                })
        }) { innerPadding ->
        MainContent(navController,innerPadding)
    }
}

@Composable
fun MainContent(
    navController: NavController,
    paddingValues: PaddingValues, movieList: List<String> = listOf(
        "Movie1",
        "Movie2",
        "Movie3",
        "Movie4",
        "Movie5",
        "Movie6",
        "Movie7",
        "Movie8",
        "Movie9",
        "Movie10",
        "Movie11",
        "Movie12",
        "Movie13",
        "Movie14",
        "Movie15",
        "Movie16",
        "Movie17",
        "Movie18"
    )
) {
    Column {
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            items(movieList) {
                MovieRow(it){movie->
                    Log.d("TAG", "MainContent: $movie")
                    navController.navigate(route = MovieScreens.DetailScreen.name)
                }
            }
        }
    }
}