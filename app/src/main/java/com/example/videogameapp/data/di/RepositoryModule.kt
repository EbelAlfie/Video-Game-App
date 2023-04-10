package com.example.videogameapp.data.di

import com.example.videogameapp.data.repository.GameRepository
import com.example.videogameapp.domain.AppRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class, LocalDbModule::class])
interface RepositoryModule {
    @Binds
    fun initializeRepository(gameRepository: GameRepository): AppRepository
}