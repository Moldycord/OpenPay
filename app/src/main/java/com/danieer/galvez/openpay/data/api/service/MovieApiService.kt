package com.danieer.galvez.openpay.data.api.service

import com.danieer.galvez.openpay.data.entities.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("search/movie")
    suspend fun getMovieByName(
        @Query("query") movieName: String,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = "7a03adca4702ab3dc849545e0c707d60"
    ): MoviesResponse


    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = "7a03adca4702ab3dc849545e0c707d60"
    ): MoviesResponse


    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = "7a03adca4702ab3dc849545e0c707d60",
        @Query("per_page") itemsByPage: Int = 20
    ): MoviesResponse
}