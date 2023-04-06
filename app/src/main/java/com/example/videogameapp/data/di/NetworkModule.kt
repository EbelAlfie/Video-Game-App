package com.example.videogameapp.data.di

import com.example.videogameapp.data.onlineservices.ApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {
    private val BASEURL = "https://api.rawg.io/api/"
    @Provides
    fun serviceInstance(): ApiService {
        return Retrofit.Builder().apply {
            baseUrl(BASEURL)
            addConverterFactory(GsonConverterFactory.create())
        }.build().create(ApiService::class.java)
    }
}