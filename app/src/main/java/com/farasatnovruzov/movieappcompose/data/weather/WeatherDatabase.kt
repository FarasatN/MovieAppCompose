package com.farasatnovruzov.movieappcompose.data.weather

import androidx.room.Database
import androidx.room.RoomDatabase
import com.farasatnovruzov.movieappcompose.model.Unit
import com.farasatnovruzov.movieappcompose.model.weather.local.Favorite

@Database(entities = [Favorite::class, Unit::class], version = 2, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}