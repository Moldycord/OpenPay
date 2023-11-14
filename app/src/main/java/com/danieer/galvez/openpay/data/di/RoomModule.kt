package com.danieer.galvez.openpay.data.di

import android.app.Application
import com.danieer.galvez.openpay.data.room.MoviesDao
import com.danieer.galvez.openpay.data.room.MoviesDataBase
import dagger.Module
import dagger.Provides

@Module
class RoomModule {

    @Provides
    fun providesRoomDatabase(application: Application): MoviesDataBase {
        return MoviesDataBase.getInstance(application)
    }

    @Provides
    fun providesMoviesDao(moviesDataBase: MoviesDataBase): MoviesDao {
        return moviesDataBase.moviesDao()
    }

    companion object {
        const val DATA_BASE_NAME = "movies_db"
    }
}