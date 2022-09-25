package com.example.challengechapter4

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.challengechapter4.databinding.FragmentHomeBinding
import com.example.challengechapter4.databinding.ItemNotesBinding
import com.example.challengechapter4.room.Notes
import com.example.challengechapter4.room.NotesDatabase
import com.google.android.material.internal.ContextUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class NotesAdapter(var listNotes : List<Notes>) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemNotesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun dataBinding(itemData : Notes){
            binding.notes = itemData
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesAdapter.ViewHolder {
        var view = ItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    var dbNotes : NotesDatabase? = null

    override fun onBindViewHolder(holder: NotesAdapter.ViewHolder, position: Int) {
        holder.dataBinding(listNotes[position])

        holder.binding.btnDeleteNotes.setOnClickListener{
            dbNotes = NotesDatabase.getInstance(it.context)

            GlobalScope.async {
                ViewModelNotes(Application()).deleteNote(listNotes[position])
                dbNotes?.noteDao()?.deleteNotes(listNotes[position])
                val nav = Navigation.findNavController(it)
                nav.run {
                    navigate(R.id.homeFragment)
                }
            }
        }

        holder.binding.btnEditNotes.setOnClickListener{
            val bundle = Bundle()
            bundle.putSerializable("note", listNotes[position])
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_editNotesFragment, bundle)
        }
    }

    override fun getItemCount(): Int = listNotes.size

    fun setData(listNotes: ArrayList<Notes>) {
        this.listNotes=listNotes
    }
}