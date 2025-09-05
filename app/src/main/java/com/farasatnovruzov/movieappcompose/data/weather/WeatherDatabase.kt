package com.farasatnovruzov.movieappcompose.data.weather

import androidx.room.Database
import androidx.room.RoomDatabase
import com.farasatnovruzov.movieappcompose.model.weather.local.Favorite
import com.farasatnovruzov.movieappcompose.model.weather.local.Unit

@Database(entities = [Favorite::class, Unit::class], version = 5, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}