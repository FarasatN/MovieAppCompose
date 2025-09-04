package com.farasatnovruzov.movieappcompose.screens.weather.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farasatnovruzov.movieappcompose.model.weather.local.Favorite
import com.farasatnovruzov.movieappcompose.repository.weather.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: WeatherDbRepository) :
    ViewModel() {

    private val _favList = MutableStateFlow<List<Favorite>>(emptyList())
    val favList = _favList.asStateFlow()

    init {
        getFavs()
    }

    private fun getFavs() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavorites().distinctUntilChanged()
                .collect { listOfFavs ->
                    if (listOfFavs.isNullOrEmpty()) {
                        Log.d("FAV", ": Empty favs ")
                    } else {
                        _favList.value = listOfFavs
                        Log.d("FAV", "favs :${favList.value} ")
                    }
                }
        }
    }

    fun insertFavorite(favorite: Favorite) = viewModelScope.launch {
        repository.insertFavorite(favorite)
        Log.d("FAV", "insertFavorite: $favorite")
    }

    fun updateFavorite(favorite: Favorite) = viewModelScope.launch {
        repository.updateFavorite(favorite)
        Log.d("FAV", "updateFavorite: $favorite")
    }

    fun deleteFavorite(favorite: Favorite) = viewModelScope.launch {
        repository.deleteFavorite(favorite)
        Log.d("FAV", "deleteFavorite: $favorite")
        getFavs()
    }

    fun getFavoriteById(city: String) = viewModelScope.launch {
        repository.getFavById(city)
        Log.d("FAV", "getFavoriteById: $city")
    }

    fun deleteAllFavorites() = viewModelScope.launch {
        repository.deleteAllFavorites()
        Log.d("FAV", "deleteAllFavorites: ")
    }
}