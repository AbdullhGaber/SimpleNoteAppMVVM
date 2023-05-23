package com.example.notesapplofcoding.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapplofcoding.databinding.NotesItemBinding
import com.example.notesapplofcoding.model.data_class.Note

class NotesRVAdapter : RecyclerView.Adapter<NotesRVAdapter.NotesViewHolder>() {
    private var mOnViewItemClick : ( (Note) -> Unit)? = null

    private val differUtil = object : DiffUtil.ItemCallback<Note>(){

        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
             return oldItem.noteId == newItem.noteId
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this , differUtil)

    fun setOnItemViewClick(onViewItemClick : ( (Note) -> Unit)?){
        mOnViewItemClick = onViewItemClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            NotesItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.onItemClick(mOnViewItemClick)
    }

    override fun getItemCount() = differ.currentList.size

    inner class NotesViewHolder(private val mBinding : NotesItemBinding) : RecyclerView.ViewHolder(mBinding.root){
        private lateinit var mNote : Note

        fun bind(note : Note){
            mBinding.tvNoteTitle.text = note.noteTitle
            mNote = note
        }

        fun onItemClick(listener : ( (Note) -> Unit)? ){
           itemView.setOnClickListener {
               listener?.invoke(mNote)
           }
        }
    }

}