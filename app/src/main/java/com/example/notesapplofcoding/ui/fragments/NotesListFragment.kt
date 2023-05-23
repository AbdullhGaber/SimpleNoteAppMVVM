package com.example.notesapplofcoding.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapplofcoding.R
import com.example.notesapplofcoding.databinding.FragmentNotesListBinding
import com.example.notesapplofcoding.ui.activities.MainActivity
import com.example.notesapplofcoding.ui.adapters.NotesRVAdapter
import com.example.notesapplofcoding.view_models.NotesViewModel

class NotesListFragment : Fragment(R.layout.fragment_notes_list) {
    private lateinit var mBinding : FragmentNotesListBinding
    private lateinit var mNotesAdapter : NotesRVAdapter
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
        mBinding = FragmentNotesListBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpNotesRV()

        observeOnNotes()

        observeOnSearchedNotes()

        onAddButtonClick()

        onNoteClick()

        onSearchTyping()

        onNoteSwipe()

    }

    private fun onNoteSwipe() {
       val callBack = setItemTouchCallBack()
       ItemTouchHelper(callBack).attachToRecyclerView(mBinding.rvNotes)
    }

    private fun setItemTouchCallBack()  : ItemTouchHelper.SimpleCallback{
        return object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition

                val note = mNotesAdapter.differ.currentList[pos]

                mViewModel.deleteNote(note)
            }

        }
    }

    private fun onSearchTyping() {
        mBinding.edSearch.addTextChangedListener {
            mViewModel.searchNotes(it.toString())
        }
    }

    private fun onNoteClick() {
          mNotesAdapter.setOnItemViewClick {
              val bundle = Bundle()

              bundle.apply {
                  putParcelable("note" , it)
              }

              findNavController().navigate(R.id.action_notesListFragment_to_noteFragment , bundle)
          }
    }

    private fun observeOnSearchedNotes() {
        lifecycleScope.launchWhenStarted {
            mViewModel.searchedNotesFlow.collect{
                mNotesAdapter.differ.submitList(it)
            }
        }
    }

    private fun observeOnNotes() {
        lifecycleScope.launchWhenStarted {
            mViewModel.notes.collect{
                mNotesAdapter.differ.submitList(it)
            }
        }
    }

    private fun onAddButtonClick() {
        mBinding.btnAddNote.setOnClickListener{
            findNavController().navigate(R.id.action_notesListFragment_to_noteFragment)
        }
    }

    private fun setUpNotesRV() {
        mNotesAdapter = NotesRVAdapter()

        mBinding.rvNotes.apply {
            adapter = mNotesAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

}