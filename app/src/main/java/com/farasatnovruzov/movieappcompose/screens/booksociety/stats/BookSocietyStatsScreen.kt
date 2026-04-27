package com.farasatnovruzov.movieappcompose.screens.booksociety.stats

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.farasatnovruzov.movieappcompose.screens.booksociety.home.BookSocietyHomeScreenViewModel


@Composable
fun BookSocietyStatsScreen(
    navController: NavController,
    viewModel: BookSocietyHomeScreenViewModel = hiltViewModel()
) {
    val listOfBooks = viewModel.data.value.data?.toMutableStateList()
    
}