package com.example.challengechapter4

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengechapter4.databinding.FragmentHomeBinding
import com.example.challengechapter4.room.Notes
import com.example.challengechapter4.room.NotesDatabase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_notes.*
import kotlinx.android.synthetic.main.item_notes.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var sharedPref: SharedPreferences
    var dbNotes: NotesDatabase? = null
    lateinit var adapterNotes : NotesAdapter
    lateinit var vmNotes : ViewModelNotes

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = requireActivity().getSharedPreferences("dataUser", Context.MODE_PRIVATE)
        txtWelcomeUser.text = sharedPref.getString("username", "You!")
        dbNotes = NotesDatabase.getInstance(requireContext())

        rvVm()

        vmNotes = ViewModelProvider(this).get(ViewModelNotes::class.java)

        vmNotes.getAllNoteObservers().observe(viewLifecycleOwner, Observer {
            adapterNotes.setData(it as ArrayList<Notes>)
        })

        binding.btnAddNotes.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_addNotesFragment)
        }

        txtLogout.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_loginFragment)
        }
    }

    fun rvVm(){
        adapterNotes = NotesAdapter(ArrayList())
        binding.rvHome.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvHome.adapter = adapterNotes
    }

    override fun onStart() {
        super.onStart()
        GlobalScope.launch {
            var data = dbNotes?.noteDao()?.getData()

            activity?.runOnUiThread {
                adapterNotes = NotesAdapter(data!!)
                binding.rvHome.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.rvHome.adapter = adapterNotes
            }
        }
    }

    fun getAllNotes(){
        GlobalScope.launch {
            var data = dbNotes?.noteDao()?.getData()

            activity?.runOnUiThread{
                adapterNotes = NotesAdapter(data!!)
                binding.rvHome.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.rvHome.adapter = adapterNotes
            }
        }

    }

}
