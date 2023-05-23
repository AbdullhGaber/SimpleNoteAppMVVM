package com.example.notesapplofcoding.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notesapplofcoding.R
import com.example.notesapplofcoding.databinding.FragmentNoteBinding
import com.example.notesapplofcoding.model.data_class.Note
import com.example.notesapplofcoding.ui.activities.MainActivity
import com.example.notesapplofcoding.view_models.NotesViewModel

class NoteFragment : Fragment(R.layout.fragment_note) {
    private lateinit var mBinding: FragmentNoteBinding
    private val args by navArgs<NoteFragmentArgs>()
    private lateinit var mViewModel : NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentNoteBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.note?.let {
           bindUI(it)
        }

        onSaveButtonClick()

        onDeleteIconClick()
    }

    private fun onDeleteIconClick() {
        mBinding.imgDeleteNote.setOnClickListener{
            val id = args.note!!.noteId
            val title = args.note!!.noteTitle
            val text = args.note!!.noteText

            Note(id , title , text).also {
                mViewModel.deleteNote(it)
            }

            findNavController().navigateUp()
        }
    }

    private fun onSaveButtonClick() {
        mBinding.btnSaveNote.setOnClickListener {
          saveNote()
        }
    }

    private fun saveNote() {

        val id = args.note?.noteId ?: 0
        val title = mBinding.edTitle.text.toString()
        val text = mBinding.edNote.text.toString()

        Note(id,title,text).also {
            if(title.isEmpty() || text.isEmpty()){
                Toast.makeText(context , "All fields must be filled" , Toast.LENGTH_LONG).show()
                return
            }

            mViewModel.upsert(it)
            findNavController().navigateUp()
        }
    }

    private fun bindUI(note: Note) {
        mBinding.apply {
            edTitle.setText(note.noteTitle)
            edNote.setText(note.noteText)
            imgDeleteNote.visibility = View.VISIBLE
        }
    }

}