package com.farasatnovruzov.movieappcompose.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.farasatnovruzov.movieappcompose.model.Note
import com.farasatnovruzov.movieappcompose.utils.Converters


@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun noteDao(): NoteDatabaseDao

}