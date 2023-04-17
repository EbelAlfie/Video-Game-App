package com.example.videogameapp.data.di

import com.example.videogameapp.data.onlineservices.GameApiService
import com.example.videogameapp.data.onlineservices.StoreApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {
    private val GAME_BASE_URL = "https://api.rawg.io/api/"
    @Provides
    fun serviceInstance(): GameApiService {
        return Retrofit.Builder().apply {
            baseUrl(GAME_BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
        }.build().create(GameApiService::class.java)
    }

    @Provides
    fun storeServiceInstance(): StoreApiService {
        return Retrofit.Builder().apply {
            baseUrl(GAME_BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
        }.build().create(StoreApiService::class.java)
    }
}