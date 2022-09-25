package com.example.challengechapter4

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.challengechapter4.room.Notes
import com.example.challengechapter4.room.NotesDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ViewModelNotes(app:Application) : AndroidViewModel(app) {

    lateinit var allNotes : MutableLiveData<List<Notes>>

    init{
        allNotes = MutableLiveData()
        getAllNote()
    }

    fun getAllNoteObservers(): MutableLiveData<List<Notes>> {
        return allNotes
    }

    fun getAllNote() {
        GlobalScope.launch {
            val userDao = NotesDatabase.getInstance(getApplication())!!.noteDao()
            val listnote = userDao.getData()
            allNotes.postValue(listnote)
        }
    }

    fun insertNote(entity: Notes){
        val noteDao = NotesDatabase.getInstance(getApplication())?.noteDao()
        noteDao!!.insertNotes(entity)
        getAllNote()
    }

    fun deleteNote(entity: Notes){
        val userDao = NotesDatabase.getInstance(getApplication())!!.noteDao()
        userDao?.deleteNotes(entity)
        getAllNote()
    }

    fun updateNote(entity: Notes){
        val userDao = NotesDatabase.getInstance(getApplication())!!.noteDao()
        userDao?.updateNotes(entity)
        getAllNote()
    }
}