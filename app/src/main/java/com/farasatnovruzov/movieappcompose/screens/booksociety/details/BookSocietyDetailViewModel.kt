package com.farasatnovruzov.movieappcompose.screens.booksociety.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farasatnovruzov.movieappcompose.data.Resource
import com.farasatnovruzov.movieappcompose.model.booksociety.Item
import com.farasatnovruzov.movieappcompose.repository.booksociety.BookSocietyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSocietyDetailViewModel @Inject constructor(private val repository: BookSocietyRepository) : ViewModel()  {
    suspend fun getBookInfo(bookId: String) : Resource<Item> {

        return repository.getBookInfo(bookId)
    }
}

