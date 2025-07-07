package com.farasatnovruzov.movieappcompose.screens.note

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.farasatnovruzov.movieappcompose.data.NotesDataSource
import com.farasatnovruzov.movieappcompose.model.Note

@RequiresApi(Build.VERSION_CODES.O)
class NoteViewModel(): ViewModel() {
    private var noteList = mutableStateListOf<Note>()
    init {
        noteList.addAll(NotesDataSource().loadNotes())
    }
    fun addNote(note: Note){
        noteList.add(note)
    }
    fun removeNote(note: Note){
        noteList.remove(note)
    }
    fun getAllNotes(): List<Note>{
        return noteList
    }

}