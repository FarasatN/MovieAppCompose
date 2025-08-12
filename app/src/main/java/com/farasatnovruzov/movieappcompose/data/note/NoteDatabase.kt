package com.farasatnovruzov.movieappcompose.data.note

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.farasatnovruzov.movieappcompose.model.note.Note
import com.farasatnovruzov.movieappcompose.utils.Converters


@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun noteDao(): NoteDatabaseDao

}