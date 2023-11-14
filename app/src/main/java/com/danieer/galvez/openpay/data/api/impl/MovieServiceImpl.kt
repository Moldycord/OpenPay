package com.danieer.galvez.openpay.data.api.impl

import com.danieer.galvez.openpay.data.api.service.MovieApiService
import com.danieer.galvez.openpay.data.entities.MoviesResponse
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieServiceImpl @Inject constructor(
    private val apiService: MovieApiService
) {

    fun getMovieByName(nameOrId: String) = flow<MoviesResponse> {
        val result = apiService.getMovieByName(nameOrId)
        emit(result)
    }

    suspend fun getPopularMovies(): MoviesResponse = apiService.getPopularMovies()

}