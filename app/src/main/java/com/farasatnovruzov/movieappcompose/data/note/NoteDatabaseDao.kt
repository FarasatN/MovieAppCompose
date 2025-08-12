package com.farasatnovruzov.movieappcompose.data.note

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.farasatnovruzov.movieappcompose.model.note.Note
import kotlinx.coroutines.flow.Flow
import java.util.UUID


@Dao
interface NoteDatabaseDao {
    @Query("SELECT * from notes_tbl")
//    fun getAllNotes(): MutableState<List<Note>> //room ile bas agrisi verecek
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * from notes_tbl where id =:id")
    suspend fun getNoteById(id: UUID): Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(note: Note)

    @Query("DELETE from notes_tbl")
    suspend fun deleteAllNotes()

    @Delete
    suspend fun deleteNote(note: Note)


}
