package com.danieer.galvez.openpay.data.di

import android.app.Application
import androidx.room.Room
import com.danieer.galvez.openpay.data.api.service.MovieApiService
import com.danieer.galvez.openpay.data.room.MoviesDao
import com.danieer.galvez.openpay.data.room.MoviesDataBase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    fun provideBaseUrl() = BASE_URL

    @Provides
    fun providesCacheSize(): Long = CACHE_SIZE.toLong()

    @Provides
    @Singleton
    fun providesCache(application: Application, cacheSize: Long): Cache {
        return Cache(application.cacheDir, cacheSize)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(cache: Cache): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val headerInterceptor: Interceptor = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val newRequest =
                    chain.request().newBuilder().addHeader("accept", "application/json").addHeader(
                        "Authorization",
                        "Bearer" + "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3YTAzYWRjYTQ3MDJhYjNkYzg0OTU0NWUwYzcwN2Q2MCIsInN1YiI6IjVjZTczYjYyYzNhMzY4MmFhZjFkYzhkMiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.aQUyXEQleDmC4yoJUueKu3Uf0rPzMJRSrtZceLHrh3E"
                    ).build()
                return chain.proceed(newRequest)
            }

        }

        return OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            .addInterceptor(headerInterceptor).cache(cache).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl).client(okHttpClient).build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): MovieApiService =
        retrofit.create(MovieApiService::class.java)

    @Provides
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO


    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val CACHE_SIZE = 10 * 1024 * 1024
    }
}
