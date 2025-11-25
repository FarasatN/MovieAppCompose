package com.farasatnovruzov.movieappcompose.screens.booksociety.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.farasatnovruzov.movieappcompose.components.booksociety.BookSocietyAppBar
import com.farasatnovruzov.movieappcompose.navigation.booksociety.BookSocietyScreens

@OptIn(ExperimentalComposeUiApi::class)
@Preview(showBackground = true)
@Composable
fun BookSocietySearchScreen(
    navController: NavController = NavController(context = LocalContext.current)
) {
    Scaffold(
        topBar = {
            BookSocietyAppBar(
                title = "Search",
                // Use Icons.AutoMirrored.Filled.ArrowBack for better LTR/RTL support
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                navController = navController,
                showProfile = false
            ) {
                // Navigate back to the previous screen
                navController.popBackStack()
            }
        }
    ) { paddingValues -> // Use the paddingValues parameter
        Surface(
            modifier = Modifier
                .fillMaxSize()
                // Apply the padding provided by the Scaffold, specifically the top padding
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
                    // For demonstration, print the search query when action is taken
                    onSearch = { query ->
                        println("Searching for: $query")
                        // TODO: Call ViewModel function to fetch data here
                    }
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
    onSearch: (String) -> Unit
){
    Column(modifier = modifier) {
        val searchQueryState = rememberSaveable { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current

        // Check if the search query is valid (non-empty after trimming)
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
                // Set the IME action to 'Search'
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions {
                if (!valid) return@KeyboardActions
                // 1. Execute the search action
                onSearch(searchQueryState.value.trim())
                // 2. Optional: Clear the input field
                searchQueryState.value = ""
                // 3. Hide the software keyboard
                keyboardController?.hide()
            }
        )
    }
}