package com.danieer.galvez.openpay.data.api.service

import com.danieer.galvez.openpay.data.entities.MoviesResponse
import com.danieer.galvez.openpay.utils.API_KEY
import com.danieer.galvez.openpay.utils.ITEMS_BY_PAGE_DEFAULT
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("search/movie")
    suspend fun getMovieByName(
        @Query("query") movieName: String,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = API_KEY
    ): MoviesResponse


    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = API_KEY
    ): MoviesResponse


    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("per_page") itemsByPage: Int = ITEMS_BY_PAGE_DEFAULT
    ): MoviesResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("per_page") itemsByPage: Int = ITEMS_BY_PAGE_DEFAULT
    ): MoviesResponse
}