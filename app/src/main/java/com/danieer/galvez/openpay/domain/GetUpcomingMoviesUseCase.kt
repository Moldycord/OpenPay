package com.danieer.galvez.openpay.domain

import com.danieer.galvez.openpay.data.entities.MoviesResponse
import com.danieer.galvez.openpay.data.repository.MovieRepository
import com.danieer.galvez.openpay.presentation.mappers.DataState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetUpcomingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository, private val dispatcher: CoroutineDispatcher
) {

    operator fun invoke(isInternedConnected: Boolean): Flow<DataState<MoviesResponse>> =
        movieRepository.getUpcomingMovies(isInternedConnected).flowOn(context = dispatcher)
}