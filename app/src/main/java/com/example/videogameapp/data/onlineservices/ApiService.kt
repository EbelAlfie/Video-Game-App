package com.example.videogameapp.data.onlineservices

import com.example.videogameapp.data.modeldata.GameDetailedModel
import com.example.videogameapp.data.modeldata.GameItemResponse
import com.example.videogameapp.data.onlineservices.ServiceUtils.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("games")
    suspend fun getAllGame(@Query("key") apiKey: String = API_KEY,
                           @Query("dates") dates: String?,
                           @Query("search") search: String?,
                           @Query("page") page: Int
    ): GameItemResponse

    //@Headers("key:" + ServiceUtils.API_KEY)
    @GET("games/{id}")
    suspend fun getGameDetail(@Path("id") id: Long,
                              @Query("key") apiKey: String = API_KEY
    ): GameDetailedModel

}