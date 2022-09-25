package com.example.challengechapter4.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Notes(
    @PrimaryKey(autoGenerate = true) var id:Int,
    var judul:String,
    var catatan: String) : Serializable
