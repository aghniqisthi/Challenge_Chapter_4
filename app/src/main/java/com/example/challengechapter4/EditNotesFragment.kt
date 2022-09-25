package com.example.challengechapter4

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.challengechapter4.databinding.FragmentEditNotesBinding
import com.example.challengechapter4.databinding.FragmentRegisterBinding
import com.example.challengechapter4.room.Notes
import com.example.challengechapter4.room.NotesDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class EditNotesFragment : Fragment() {
    lateinit var binding: FragmentEditNotesBinding
    var dbNotes: NotesDatabase? = null
    lateinit var vmNotes : ViewModelNotes

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEditNotesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dbNotes = NotesDatabase.getInstance(requireContext())
        vmNotes = ViewModelProvider(this).get(ViewModelNotes::class.java)

        var getData = arguments?.getSerializable("note") as Notes
        binding.editTitleEdit.setText(getData.judul)
        binding.editContentEdit.setText(getData.catatan)

        binding.btnSaveNotesEdit.setOnClickListener {
            editNotes()
        }
    }

    fun editNotes(){
        GlobalScope.async {
            var getData = arguments?.getSerializable("note") as Notes

            var title = binding.editTitleEdit.text.toString()
            var note = binding.editContentEdit.text.toString()

            val editNote = Notes(getData.id, title, note)
            vmNotes.updateNote(editNote)

            Navigation.findNavController(requireView()).navigate(R.id.action_editNotesFragment_to_homeFragment)
        }
    }
}