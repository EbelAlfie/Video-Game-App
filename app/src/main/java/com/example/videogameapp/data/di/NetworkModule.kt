package com.example.videogameapp.data.di

import com.example.videogameapp.data.onlineservices.GameApiService
import com.example.videogameapp.data.onlineservices.QueryParamService
import com.example.videogameapp.data.onlineservices.StoreApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {
    private val loggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

    private val GAME_BASE_URL = "https://api.rawg.io/api/"
    @Provides
    fun gameServiceInstance(): GameApiService {
        return Retrofit.Builder().apply {
            baseUrl(GAME_BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            client(client)
        }.build().create(GameApiService::class.java)
    }

    @Provides
    fun storeServiceInstance(): StoreApiService {
        return Retrofit.Builder().apply {
            baseUrl(GAME_BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
/*
            client(client)
*/
        }.build().create(StoreApiService::class.java)
    }

    @Provides
    fun queryParamServiceInstance(): QueryParamService {
        return Retrofit.Builder().apply {
            baseUrl(GAME_BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            client(client)
        }.build().create(QueryParamService::class.java)
    }
}