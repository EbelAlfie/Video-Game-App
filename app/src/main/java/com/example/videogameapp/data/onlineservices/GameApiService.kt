package com.example.videogameapp.data.onlineservices

import com.example.videogameapp.data.modeldata.gamedatamodel.*
import com.example.videogameapp.data.onlineservices.ServiceUtils.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GameApiService {

    @GET("games")
    suspend fun getAllGame(@Query("key") apiKey: String = API_KEY,
                           @Query("dates") dates: String?,
                           @Query("search") search: String?,
                           @Query("page") page: Int?,
                           @Query("platform") platform: String?,
                           @Query("store") store: String?,
                           @Query("ordering") ordering: String?,
    ): GameItemResponse

    @GET("games/{id}")
    suspend fun getGameDetail(@Path("id") id: Long,
                              @Query("key") apiKey: String = API_KEY
    ): GameDetailedModel

    @GET("{id}/screenshots")
    suspend fun getGameDetailScreenshots(@Path("id") id: Long,
                               @Query("key") apiKey: String = API_KEY
    ): ScreenshotDetailResponseModel

    @GET("{id}/movies")
    suspend fun getTrailers(@Path("id") id: Long,
                            @Query("key") apiKey: String = API_KEY)

    @GET("games/{id}/stores")
    suspend fun getGameStoreLink(@Path("id") id: Long,
                                 @Query("key") apiKey: String = API_KEY): GameStoreResponse

    @GET("games/{id}/additions")
    suspend fun getGameDlc(@Path("id") id: Long,
                           @Query("key") apiKey: String = API_KEY): DlcResponse

}