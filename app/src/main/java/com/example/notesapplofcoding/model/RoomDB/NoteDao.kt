package com.example.notesapplofcoding.model.RoomDB

import androidx.room.*
import com.example.notesapplofcoding.model.data_class.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(note : Note)

    @Delete
    suspend fun deleteNote(note : Note)

    @Query("SELECT * FROM notes ORDER BY noteId DESC")
    fun getNotes() : Flow<List<Note?>>

    @Query("SELECT * FROM notes WHERE noteTitle LIKE '%' || :searchedNote || '%' ")
    fun searchNotes(searchedNote : String) : Flow<List<Note?>>

    @Query("DELETE FROM notes")
    suspend fun deleteAllNotes()
}