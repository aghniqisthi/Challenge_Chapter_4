package com.example.challengechapter4.room

import androidx.room.*

@Dao
interface NotesDAO {

    @Query("SELECT * FROM Notes ORDER BY id DESC")
    fun getData() : List<Notes>

    @Insert
    fun insertNotes(notes : Notes)

    @Delete
    fun deleteNotes(notes: Notes)

    @Update
    fun updateNotes(notes: Notes)
}