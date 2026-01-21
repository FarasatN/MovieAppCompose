package com.farasatnovruzov.movieappcompose.screens.booksociety.search

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farasatnovruzov.movieappcompose.data.DataOrException
import com.farasatnovruzov.movieappcompose.data.Resource
import com.farasatnovruzov.movieappcompose.model.booksociety.Item
import com.farasatnovruzov.movieappcompose.repository.booksociety.BookSocietyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSocietySearchViewModel @Inject constructor(private val repository: BookSocietyRepository) :
    ViewModel() {
//    val listOfBooks: MutableState<Resource<List<Item>>> = mutableStateOf(Resource.Loading(data = null))
//    val bookInfo: MutableState<Resource<Item>> = mutableStateOf(Resource.Loading(data = null))

//    val listOfBooks: MutableState<DataOrException<List<Item>, Boolean, Exception>> =
//        mutableStateOf(DataOrException(null, true, Exception("")))
//    val bookInfo: MutableState<DataOrException<Item, Boolean, Exception>> =
//        mutableStateOf(DataOrException(null, true, Exception("")))
//
//    init {
//        searchBooks("kotlin")
//    }
//
//     fun searchBooks(query: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            if (query.isEmpty()) {
//                return@launch
//            }
//            listOfBooks.value.loading = true
//            listOfBooks.value = repository.getBooks(query)
//            Log.d("Search", "searchBooks: ${listOfBooks.value.data}")
//            if (listOfBooks.value.data.toString().isNotEmpty()) {
//                listOfBooks.value.loading = false
//            }
//        }
//    }

    var list: List<Item> by mutableStateOf(listOf())
    var isLoading: Boolean by mutableStateOf(true)

//var list: MutableState<Resource<List<Item>>> = mutableStateOf(Resource.Loading(data = null))

    init {
        loadBooks()
    }
    private fun loadBooks() {
        searchBooks("kotlin")
    }

    fun searchBooks(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading = true
            if (query.isEmpty()) {
                return@launch
            }
            try {
                when (val response = repository.getBooks(query)) {
                    is Resource.Success -> {
                        list = response.data!!
                        if (list.isNotEmpty())isLoading = false

                    }
                    is Resource.Error -> {
                        isLoading = false
                        Log.d("Network", "searchBooks: ${response.message}")
                    }
                    else -> {isLoading = false}
                }
            } catch (exception: Exception) {
                isLoading = false
                Log.d(
                    "Network",
                    "searchBooks: ${exception.message.toString()}: Failed getting books"
                )

            }
        }
    }


}