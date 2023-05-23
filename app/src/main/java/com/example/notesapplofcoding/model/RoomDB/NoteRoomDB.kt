package com.example.notesapplofcoding.model.RoomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesapplofcoding.model.data_class.Note

@Database(entities = [Note::class] , version = 1)
abstract class NoteRoomDB : RoomDatabase(){
    abstract fun getNoteDao() : NoteDao

    companion object{
        @Volatile
        private  var mInstance : NoteRoomDB? = null

        @Synchronized
        fun getInstance(context : Context) : NoteRoomDB{
            if(mInstance == null){
                mInstance = Room.databaseBuilder(context,NoteRoomDB::class.java , "note.db").
                fallbackToDestructiveMigration().
                build()
            }

            return mInstance!!
        }
    }
}