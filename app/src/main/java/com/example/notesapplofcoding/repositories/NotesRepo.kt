package com.example.notesapplofcoding.repositories

import com.example.notesapplofcoding.model.RoomDB.NoteRoomDB
import com.example.notesapplofcoding.model.data_class.Note

class NotesRepo (
    noteDB : NoteRoomDB
        ) {

    private val noteDao = noteDB.getNoteDao()

    suspend fun upsert(note : Note) = noteDao.upsert(note)

    suspend fun deleteNote(note : Note) = noteDao.deleteNote(note)

    suspend fun deleteAllNotes() = noteDao.deleteAllNotes()

    fun getNotes() = noteDao.getNotes()

    fun searchNotes(searchedNote : String) = noteDao.searchNotes(searchedNote)


}