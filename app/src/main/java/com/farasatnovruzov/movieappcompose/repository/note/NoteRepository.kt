package com.farasatnovruzov.movieappcompose.repository.note

import com.farasatnovruzov.movieappcompose.data.note.NoteDatabaseDao
import com.farasatnovruzov.movieappcompose.model.note.Note
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import java.util.UUID

class NoteRepository @Inject constructor(private val noteDatabaseDao: NoteDatabaseDao) {


    suspend fun addNote(note: Note) = noteDatabaseDao.insert(note)
    suspend fun updateNote(note: Note) = noteDatabaseDao.update(note)
    suspend fun deleteNote(note: Note) = noteDatabaseDao.deleteNote(note)
    suspend fun deleteAllNotes() = noteDatabaseDao.deleteAllNotes()
    suspend fun getNoteById(id: UUID): Note = noteDatabaseDao.getNoteById(id)
    //suspend is not necessary
    fun getAllNotes(): Flow<List<Note>> = noteDatabaseDao.getAllNotes().flowOn(Dispatchers.IO).conflate()
}