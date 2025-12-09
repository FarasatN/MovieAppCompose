package com.farasatnovruzov.movieappcompose.screens.booksociety.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.farasatnovruzov.movieappcompose.components.booksociety.BookListCard
import com.farasatnovruzov.movieappcompose.components.booksociety.BookSocietyAppBar
import com.farasatnovruzov.movieappcompose.model.booksociety.MBook

@OptIn(ExperimentalComposeUiApi::class)
@Preview(showBackground = true)
@Composable
fun BookSocietySearchScreen(
    navController: NavController = NavController(context = LocalContext.current)
) {
    // State to hold the submitted search query
    var searchQuery by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            BookSocietyAppBar(
                title = "Search",
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                navController = navController,
                showProfile = false
            ) {
                navController.popBackStack()
            }
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top
            ) {
                SearchForm(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    // onSearch now updates the state and triggers logic
                    onSearch = { query ->
                        searchQuery = query // Update the state with the submitted query
                        println("Searching for: $query")
                        // TODO: Call ViewModel function to fetch data here
                    }
                )

                // The BookList should be rendered based on the submitted query state
                Spacer(modifier = Modifier.height(13.dp))
                if (searchQuery.isNotEmpty()) {
                    // Show results list only after a search has been executed
                    Text(
                        text = "Results for: $searchQuery",
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    // Now calling a composable (BookList) from a composable context (Column)
                    BookList(navController = navController)
                }
            }
        }
    }
}

@Composable
fun BookList(navController: NavController) {
    // Placeholder implementation
    val listOfBooks = listOf(
        MBook(id = "dadfa", title = "Hello Again", authors = "All of us"),
        MBook(id = "dadfa", title = "Hello Again", authors = "All of us"),
        MBook(id = "dadfa", title = "Hello Again", authors = "All of us"),
        MBook(id = "dadfa", title = "Hello Again", authors = "All of us"),
        MBook(id = "dadfa", title = "Hello Again", authors = "All of us"),
        MBook(id = "dadfa", title = "Hello Again", authors = "All of us"),
    )
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(listOfBooks) { index ->
            BookRow(book = index, navController = navController)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun BookRow(book: MBook, navController: NavController) {
    Card(
        modifier = Modifier
            .clickable {

            }
            .fillMaxWidth()
            .height(100.dp)
            .padding(3.dp),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp // <-- This is the shadow depth
        )
    ) {
        Row(
            modifier = Modifier.padding(5.dp),
            verticalAlignment = androidx.compose.ui.Alignment.Top
        ) {
            val imageUrl: String =
                "http://books.google.com/books/content?id=UeR4DO_HvgoC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = "Book Image",
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .padding(end = 3.dp)
            )
            Column() {
                Text(
                    text = book.title.toString(),
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(4.dp)
                )
                Text(
                    text = "Author: ${book.authors.toString()}",
                    overflow = TextOverflow.Clip,
                    style = TextStyle(
                        fontSize = 16.sp
                    ),
                    modifier = Modifier.padding(4.dp)
                )

            }
        }
    }


}


@ExperimentalComposeUiApi
@Composable
fun SearchForm(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    hint: String = "Search",
    // FIX: Changed signature to non-Composable function type
    onSearch: (String) -> Unit
) {
    Column(modifier = modifier) {
        val searchQueryState = rememberSaveable { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current

        val valid = remember(searchQueryState.value) {
            searchQueryState.value.trim().isNotEmpty()
        }

        OutlinedTextField(
            value = searchQueryState.value,
            onValueChange = { searchQueryState.value = it },
            label = { Text(text = hint) },
            enabled = !loading,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions {
                if (!valid) return@KeyboardActions
                // 1. Execute the search action (calls the non-Composable lambda)
                onSearch(searchQueryState.value.trim())

                // 2. Optional: Clear the input field
                // Note: Clearing immediately might not be desired if you want the query visible
                // searchQueryState.value = ""

                // 3. Hide the software keyboard
                keyboardController?.hide()
            }
        )
    }
}