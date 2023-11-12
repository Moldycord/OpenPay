package com.danieer.galvez.openpay.presentation.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danieer.galvez.openpay.data.entities.MoviesResponse
import com.danieer.galvez.openpay.domain.GetMovieUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MovieSearchViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase
) : ViewModel() {

    private val _movieData = MutableLiveData<MoviesResponse>()
    val moviesData: LiveData<MoviesResponse> get() = _movieData


    fun getMovieByName(nameOrId: String) {
        getMovieUseCase(nameOrId).onEach { _movieData.value = it }
            .catch { println(it.message) }
            .launchIn(viewModelScope)
    }


}