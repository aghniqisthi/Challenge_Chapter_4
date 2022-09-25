package com.example.challengechapter4.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Notes::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun noteDao() : NotesDAO

    companion object{
        private var INSTANCE : NotesDatabase? = null

        fun getInstance(context : Context):NotesDatabase? {
            if (INSTANCE == null){
                synchronized(NotesDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        NotesDatabase::class.java,"NoteApp.db").build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}