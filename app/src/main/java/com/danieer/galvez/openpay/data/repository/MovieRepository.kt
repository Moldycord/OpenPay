package com.danieer.galvez.openpay.data.repository

import com.danieer.galvez.openpay.data.api.impl.MovieServiceImpl
import com.danieer.galvez.openpay.data.api.service.MovieApiService
import com.danieer.galvez.openpay.data.entities.Movie
import com.danieer.galvez.openpay.data.entities.MovieRoom
import com.danieer.galvez.openpay.data.entities.MoviesResponse
import com.danieer.galvez.openpay.data.room.MoviesDao
import com.danieer.galvez.openpay.presentation.mappers.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApiService: MovieServiceImpl,
    private val movieApiService2: MovieApiService,
    private val moviesDao: MoviesDao
) {

    fun getMovieByName(name: String): Flow<MoviesResponse> = movieApiService.getMovieByName(name)

    fun getPopularMovies(isInternedConnected: Boolean): Flow<MoviesResponse> = flow {

        if (isInternedConnected) {
            try {
                val result = movieApiService.getPopularMovies()
                saveMovies(result.movies, POPULAR_MOVIES_TYPE)

                emit(result)
            } catch (e: Exception) {
                println(e.message)
            }
        } else {
            val moviesRoom = getMoviesByType(POPULAR_MOVIES_TYPE)
            emit(MoviesResponse(0, moviesRoom))
        }
    }

    fun getRatedMovies(isInternedConnected: Boolean): Flow<DataState<MoviesResponse>> = flow {

        emit(DataState.Loading)

        if (isInternedConnected) {
            try {
                val result = movieApiService2.getTopRatedMovies()
                saveMovies(result.movies, RATED_MOVIES_TYPE)
                emit(DataState.Success(result))

            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        } else {
            val moviesRoom = getMoviesByType(RATED_MOVIES_TYPE)
            emit(DataState.Success(MoviesResponse(0, moviesRoom)))
        }
    }

    fun getUpcomingMovies(isInternedConnected: Boolean): Flow<DataState<MoviesResponse>> = flow {
        emit(DataState.Loading)
        if (isInternedConnected) {
            try {
                val result = movieApiService2.getUpcomingMovies()
                saveMovies(result.movies, UPCOMING_MOVIES_TYPE)

                emit(DataState.Success(result))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        } else {
            val moviesRoom = getMoviesByType(RATED_MOVIES_TYPE)
            emit(DataState.Success(MoviesResponse(0, moviesRoom)))
        }

    }

    private fun getMoviesByType(movieType: String): List<Movie> {
        val result = moviesDao.getMoviesByType(movieType)
        return result.map {
            Movie(
                it.movieId, it.title, it.posterPath, it.overview, it.releaseDate
            )
        }
    }

    private suspend fun saveMovies(movieList: List<Movie>, movieType: String) {
        val roomList = movieList.map {
            MovieRoom(
                movieId = it.id,
                title = it.title ?: "",
                posterPath = it.title ?: "",
                overview = it.overview ?: "",
                releaseDate = it.overview ?: "",
                movieType = movieType
            )
        }
        for (movie in roomList) {
            moviesDao.insert(movie)
        }

    }

    companion object {
        const val POPULAR_MOVIES_TYPE = "POPULAR_MOVIES"
        const val RATED_MOVIES_TYPE = "RATED_MOVIES"
        const val UPCOMING_MOVIES_TYPE = "UPCOMING_MOVIES"
    }
}