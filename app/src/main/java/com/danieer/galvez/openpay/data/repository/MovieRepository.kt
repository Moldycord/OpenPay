package com.danieer.galvez.openpay.data.repository

import com.danieer.galvez.openpay.data.api.impl.MovieServiceImpl
import com.danieer.galvez.openpay.data.api.service.MovieApiService
import com.danieer.galvez.openpay.data.entities.MoviesResponse
import com.danieer.galvez.openpay.presentation.mappers.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApiService: MovieServiceImpl,
    private val movieApiService2: MovieApiService
) {

    fun getMovieByName(name: String): Flow<MoviesResponse> =
        movieApiService.getMovieByName(name)

    fun getPopularMovies(): Flow<MoviesResponse> =
        movieApiService.getPopularMovies()

    fun getRatedMovies(): Flow<DataState<MoviesResponse>> = flow {

        emit(DataState.Loading)

        try {
            val result = movieApiService2.getTopRatedMovies()
            emit(DataState.Success(result))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}