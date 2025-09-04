package com.farasatnovruzov.movieappcompose.screens.weather.settings

import androidx.lifecycle.ViewModel
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.farasatnovruzov.movieappcompose.model.Unit
import com.farasatnovruzov.movieappcompose.repository.weather.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: WeatherDbRepository) :
    ViewModel() {

    private val _unitList = MutableStateFlow<List<Unit>>(emptyList())
    val unitList = _unitList.asStateFlow()

    init {
        getUnits()
    }

    private fun getUnits() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUnits().distinctUntilChanged()
                .collect { listOfUnits ->
                    if (listOfUnits.isNullOrEmpty()) {
                        Log.d("UNIT", ": Empty units ")
                    } else {
                        _unitList.value = listOfUnits
                        Log.d("UNIT", "units :${unitList.value} ")
                    }
                }
        }
    }

    fun insertUnit(unit: Unit) = viewModelScope.launch {
        repository.insertUnit(unit)
        Log.d("UNIT", "insertUnit: $unit")
    }

    fun updateUnit(unit: Unit) = viewModelScope.launch {
        repository.updateUnit(unit)
        Log.d("UNIT", "updateUnit: $unit")
    }

    fun deleteUnit(unit: Unit) = viewModelScope.launch {
        repository.deleteUnit(unit)
        Log.d("UNIT", "deleteUnit: $unit")
        getUnits()
    }

    fun deleteAllUnits() = viewModelScope.launch {
        repository.deleteAllUnits()
        Log.d("UNIT", "deleteAllUnits: ")
    }
}