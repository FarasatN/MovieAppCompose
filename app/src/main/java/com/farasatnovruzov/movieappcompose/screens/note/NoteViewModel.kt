package com.farasatnovruzov.movieappcompose.screens.note

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farasatnovruzov.movieappcompose.model.note.Note
import com.farasatnovruzov.movieappcompose.repository.note.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {
    //private var noteList = mutableStateListOf<Note>()
//    init {
//        noteList.addAll(NotesDataSource().loadNotes())
//    }
//    fun addNote(note: Note){
//        noteList.add(note)
//    }
//    fun removeNote(note: Note){
//        noteList.remove(note)
//    }
//    fun getAllNotes(): List<Note>{
//        return noteList
//    }

    private val _noteList = MutableStateFlow<List<Note>>(emptyList())
    val noteList = _noteList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllNotes().distinctUntilChanged().collect { listOfNotes ->
                if (listOfNotes.isNullOrEmpty()) {
                    _noteList.value = emptyList()
                    Log.d("Empty", ": Empty list")
                } else {
                    _noteList.value = listOfNotes
                    Log.d("List", ": ${noteList.value}")
                }
            }
        }
    }

    fun addNote(note: Note) = viewModelScope.launch { repository.addNote(note) }
    fun updateNote(note: Note) = viewModelScope.launch { repository.updateNote(note) }
    fun removeNote(note: Note) = viewModelScope.launch { repository.deleteNote(note) }
    fun deleteAllNotes() = viewModelScope.launch { repository.deleteAllNotes() }
    fun getNoteById(id: UUID) = viewModelScope.launch { repository.getNoteById(id) }


}