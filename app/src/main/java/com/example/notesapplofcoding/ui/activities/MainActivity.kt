package com.example.notesapplofcoding.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.notesapplofcoding.databinding.ActivityMainBinding
import com.example.notesapplofcoding.model.RoomDB.NoteRoomDB
import com.example.notesapplofcoding.view_models.NotesViewModel
import com.example.notesapplofcoding.view_models.view_model_provider_factory.NotesViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityMainBinding

    val viewModel : NotesViewModel by lazy {
        val viewModelFactory = NotesViewModelFactory(NoteRoomDB.getInstance(this))
        ViewModelProvider(this , viewModelFactory)[NotesViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mBinding.root)


    }
}