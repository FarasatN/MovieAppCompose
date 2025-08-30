package com.farasatnovruzov.movieappcompose.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.farasatnovruzov.movieappcompose.data.weather.WeatherDao
import com.farasatnovruzov.movieappcompose.model.weather.local.Favorite


@Database(entities = [Favorite::class], version = 1, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}