package com.example.notesapplofcoding.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapplofcoding.model.RoomDB.NoteRoomDB
import com.example.notesapplofcoding.model.data_class.Note
import com.example.notesapplofcoding.repositories.NotesRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotesViewModel(
    noteDB : NoteRoomDB
) : ViewModel() {

    private val notesRepo : NotesRepo by lazy{
        NotesRepo(noteDB)
    }

    private val _searchedNotesFlow = MutableStateFlow<List<Note?>>(emptyList())

    val searchedNotesFlow : StateFlow<List<Note?>> = _searchedNotesFlow

    val notes = notesRepo.getNotes()

    fun upsert(note : Note){
       viewModelScope.launch {
           notesRepo.upsert(note)
       }
    }

     fun deleteNote(note : Note) {
         viewModelScope.launch {
             notesRepo.deleteNote(note)
         }
     }

     fun deleteALlNotes() {
         viewModelScope.launch{
             notesRepo.deleteAllNotes()
         }
     }

    fun searchNotes(searchedNote : String) {
        viewModelScope.launch {
            notesRepo.searchNotes(searchedNote).collect{
                _searchedNotesFlow.emit(it)
            }
        }
    }

}