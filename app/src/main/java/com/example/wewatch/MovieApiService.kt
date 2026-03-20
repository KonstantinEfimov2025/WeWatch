package com.example.wewatch

import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    // Поиск фильмов по названию (параметр 's')
    // apiKey мы будем передавать при вызове
    @GET("/")
    suspend fun searchMovies(
        @Query("s") query: String,
        @Query("apikey") apiKey: String = "3034c149"
    ): MovieResponse
}