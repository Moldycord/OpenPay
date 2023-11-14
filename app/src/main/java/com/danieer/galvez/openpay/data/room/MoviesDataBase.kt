package com.danieer.galvez.openpay.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.danieer.galvez.openpay.data.entities.MovieRoom

@Database(entities = [MovieRoom::class], version = 1)
abstract class MoviesDataBase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao

    companion object {
        private var instance: MoviesDataBase? = null

        fun getInstance(context: Context): MoviesDataBase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    MoviesDataBase::class.java, "movies_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }
}