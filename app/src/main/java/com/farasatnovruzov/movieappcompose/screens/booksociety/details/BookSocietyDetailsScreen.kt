package com.farasatnovruzov.movieappcompose.screens.booksociety.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.farasatnovruzov.movieappcompose.components.booksociety.BookSocietyAppBar
import com.farasatnovruzov.movieappcompose.data.Resource
import com.farasatnovruzov.movieappcompose.model.booksociety.Item
import com.farasatnovruzov.movieappcompose.navigation.booksociety.BookSocietyScreens


@Composable
fun BookSocietyDetailsScreen(
    navController: NavController,
    bookId: String,
    viewModel: BookSocietyDetailViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        BookSocietyAppBar(
            title = "Book Details",
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            navController = navController,
            showProfile = false
        ) {
//            navController.popBackStack()
            navController.navigate(BookSocietyScreens.SearchScreen.name)
        }
    }) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
            ) {
//                var bookInfo by remember { mutableStateOf<Resource<Item>>(Resource.Loading()) }
//                LaunchedEffect(bookId) {
//                    bookInfo = viewModel.getBookInfo(bookId)
//                }
                val bookInfo = produceState<Resource<Item>>(initialValue = Resource.Loading()) {
                    value = viewModel.getBookInfo(bookId)
                }.value

                if (bookInfo.data?.volumeInfo?.title.isNullOrEmpty()) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                        Text(text = "Loading...")
                    }
                } else {
//                    Text(text = "Book Id: ${bookInfo.data.volumeInfo.title}")
                    ShowBookDetails(bookInfo, navController)
                }

            }

        }
    }

}

@Composable
fun ShowBookDetails(bookInfo: Resource<Item>, navController: NavController) {
    val bookData = bookInfo.data?.volumeInfo
    val googleBookId = bookInfo.data?.id
    Card(
        modifier = Modifier.padding(34.dp),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = CircleShape
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = bookData?.imageLinks?.thumbnail),
            contentDescription = "Book Image",
            modifier = Modifier
                .width(90.dp)
                .height(90.dp)
                .padding(1.dp)
        )
    }
    bookData?.title?.let {
        Text(
            text = it,
            fontSize = 20.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
            maxLines = 19
        )
    }
    Text(text = "Authors: ${bookData?.authors}")
    Text(text = "Page Count: ${bookData?.pageCount}")
    Text(text = "Categories: ${bookData?.categories}")
    Text(text = "Published Date: ${bookData?.publishedDate}")
    Spacer(
        modifier = Modifier
            .height(5.dp)
    )

}