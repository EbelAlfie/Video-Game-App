package com.example.videogameapp.data.onlineservices

import com.example.videogameapp.data.modeldata.storedatamodel.StoreDetailModel
import com.example.videogameapp.data.modeldata.storedatamodel.StoreItemResponseModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StoreApiService {
    @GET("stores/{id}")
    suspend fun getDetailedStoreData(@Path("id") id: Long,
                             @Query("key") apiKey: String = ServiceUtils.API_KEY
    ): StoreDetailModel

    @GET("stores")
    suspend fun getAllStore(@Query("key") apiKey: String = ServiceUtils.API_KEY) : StoreItemResponseModel
}