package com.danieer.galvez.openpay.data.entities

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("page")
    val page: Int? = 0,
    @SerializedName("results")
    val movies: List<Movie> = emptyList(),
)