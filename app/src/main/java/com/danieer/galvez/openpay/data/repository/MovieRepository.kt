package com.danieer.galvez.openpay.data.repository

import com.danieer.galvez.openpay.data.api.impl.MovieServiceImpl
import com.danieer.galvez.openpay.data.entities.MoviesResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApiService: MovieServiceImpl
) {

    fun getMovieByName(name: String): Flow<MoviesResponse> =
        movieApiService.getMovieByName(name)

    fun getPopularMovies(): Flow<MoviesResponse> =
        movieApiService.getPopularMovies()
}