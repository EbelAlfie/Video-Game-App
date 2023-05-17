package com.example.videogameapp.data.onlineservices

import retrofit2.http.GET
import retrofit2.http.Query

interface QueryParamService {
    @GET("platforms")
    suspend fun getPlatforms(@Query("ordering") order: String, @Query("key") apiKey: String = ServiceUtils.API_KEY)

    @GET("genres")
    suspend fun getGenres(@Query("ordering") order: String, @Query("key") apiKey: String = ServiceUtils.API_KEY)


}