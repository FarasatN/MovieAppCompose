package com.farasatnovruzov.movieappcompose.screens.booksociety.update

import android.util.Log
import androidx.compose.foundation.layout.Arrangement.Top
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.farasatnovruzov.movieappcompose.components.booksociety.BookSocietyAppBar
import com.farasatnovruzov.movieappcompose.data.DataOrException
import com.farasatnovruzov.movieappcompose.model.booksociety.MBook
import com.farasatnovruzov.movieappcompose.screens.booksociety.home.BookSocietyHomeScreenViewModel
import com.farasatnovruzov.movieappcompose.ui.theme.CustomBlue


@Composable
fun BookSocietyUpdateScreen(
    navController: NavController,
    bookItemId: String,
    viewModel: BookSocietyHomeScreenViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            BookSocietyAppBar(
                title = "Book Update",
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                showProfile = false,
                navController = navController,
//                elevation = 10.dp,
                onBackArrowClicked = {
                    navController.popBackStack()
                },
//                padding = 8.dp,
            )
        }
    ) {
        val bookInfo = produceState<DataOrException<List<MBook>, Boolean, Exception>>(
            initialValue = DataOrException(data = emptyList(),loading = true, e = null)
        ) {
            value = viewModel.data.value
        }.value

        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(3.dp),
                verticalArrangement = Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Log.d("BookSocietyUpdateScreen", "BookSocietyUpdateScreen: ${viewModel.data.value.data.toString()}")
                if (bookInfo.loading == true){
                    LinearProgressIndicator()
                    bookInfo.loading = false
                } else {
                    Text(text = viewModel.data.value.data?.get(0)?.title.toString())
                }
            }
        }
    }
    
}