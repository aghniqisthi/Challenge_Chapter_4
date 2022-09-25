package com.example.challengechapter4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.challengechapter4.databinding.FragmentAddNotesBinding
import com.example.challengechapter4.room.Notes
import com.example.challengechapter4.room.NotesDatabase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class AddNotesFragment : Fragment() {
    lateinit var binding: FragmentAddNotesBinding
    var dbNotes: NotesDatabase? = null
    lateinit var vmNotes : ViewModelNotes

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddNotesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbNotes = NotesDatabase.getInstance(requireContext())
        vmNotes = ViewModelProvider(this).get(ViewModelNotes::class.java)

        binding.btnSaveNotes.setOnClickListener {
            addNote()
            Navigation.findNavController(view).navigate(R.id.action_addNotesFragment_to_homeFragment)
        }
    }

    fun addNote(){
        GlobalScope.async {
            var title = binding.editTitle.text.toString()
            var note = binding.editContent.text.toString()
            val newNote = Notes(0, title, note)
            vmNotes.insertNote(newNote)
        }
    }
}