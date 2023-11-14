package com.danieer.galvez.openpay.domain

import com.danieer.galvez.openpay.data.entities.MoviesResponse
import com.danieer.galvez.openpay.data.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val dispatcher: CoroutineDispatcher
) {
    operator fun invoke(isInternetConnected: Boolean): Flow<MoviesResponse> =
        movieRepository.getPopularMovies(isInternetConnected).flowOn(context = dispatcher)
}