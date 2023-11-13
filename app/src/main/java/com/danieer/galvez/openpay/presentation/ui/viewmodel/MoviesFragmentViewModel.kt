package com.danieer.galvez.openpay.presentation.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danieer.galvez.openpay.data.entities.MoviesResponse
import com.danieer.galvez.openpay.domain.GetPopularMoviesUseCase
import com.danieer.galvez.openpay.domain.GetRatedMoviesUseCase
import com.danieer.galvez.openpay.presentation.mappers.DataState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MoviesFragmentViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getRatedMoviesUseCase: GetRatedMoviesUseCase
) : ViewModel() {

    private val _movieData = MutableLiveData<MoviesResponse>()
    val moviesData: LiveData<MoviesResponse> get() = _movieData

    private val _ratedMovies = MutableLiveData<DataState<MoviesResponse>>()
    val ratedMoviesData: LiveData<DataState<MoviesResponse>> get() = _ratedMovies

    fun getPopularMovies() {
        getPopularMoviesUseCase().onEach {
            _movieData.value = it
        }.catch { println(it.message) }.launchIn(viewModelScope)
    }

    fun getRatedMovies() {
        getRatedMoviesUseCase().onEach {
            _ratedMovies.value = it
        }.catch { println(it.message) }.launchIn(viewModelScope)
    }

}