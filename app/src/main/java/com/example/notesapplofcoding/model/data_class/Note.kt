package com.example.notesapplofcoding.model.data_class

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity("notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
     val noteId : Int = 0,
     val noteTitle : String,
     val noteText : String
) : Parcelable