package com.danieer.galvez.openpay.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieRoom(

    @PrimaryKey(autoGenerate = true)
    val primaryId: Long = 0,
    val movieId: Int = 0,
    val title: String = "",
    val posterPath: String = "",
    val overview: String = "",
    val releaseDate: String = "",
    val movieType: String = ""
)