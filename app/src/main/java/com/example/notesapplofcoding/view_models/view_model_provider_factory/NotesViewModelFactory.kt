package com.example.notesapplofcoding.view_models.view_model_provider_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesapplofcoding.model.RoomDB.NoteRoomDB
import com.example.notesapplofcoding.view_models.NotesViewModel

class NotesViewModelFactory(
    private val noteRoomDB: NoteRoomDB
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotesViewModel(noteRoomDB) as T
    }
}