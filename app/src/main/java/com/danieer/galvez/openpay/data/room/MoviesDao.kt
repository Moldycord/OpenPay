package com.danieer.galvez.openpay.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.danieer.galvez.openpay.data.entities.MovieRoom

@Dao
interface MoviesDao {

    @Insert
    suspend fun insert(movie: MovieRoom)

    @Query("SELECT * FROM movies WHERE movieType == :movieType")
    fun getMoviesByType(movieType: String): List<MovieRoom>
}