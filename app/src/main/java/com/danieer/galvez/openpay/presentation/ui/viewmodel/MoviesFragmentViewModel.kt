package com.danieer.galvez.openpay.presentation.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danieer.galvez.openpay.data.entities.MoviesResponse
import com.danieer.galvez.openpay.domain.GetPopularMoviesUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MoviesFragmentViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    private val _movieData = MutableLiveData<MoviesResponse>()
    val moviesData: LiveData<MoviesResponse> get() = _movieData

    fun getPopularMovies() {
        getPopularMoviesUseCase().onEach {
            _movieData.value = it
        }.catch { println(it.message) }
            .launchIn(viewModelScope)
    }
}