package com.danieer.galvez.openpay.presentation.mappers

sealed class DataState<out T> {
    object Loading : DataState<Nothing>()
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val exception: Exception) : DataState<Nothing>()
}