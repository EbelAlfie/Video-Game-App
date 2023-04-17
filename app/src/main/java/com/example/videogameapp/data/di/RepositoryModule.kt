package com.example.videogameapp.data.di

import com.example.videogameapp.data.repository.GameRepositoryInst
import com.example.videogameapp.data.repository.StoreRepositoryInst
import com.example.videogameapp.domain.interfaces.GameRepository
import com.example.videogameapp.domain.interfaces.StoreRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class, LocalDbModule::class])
interface RepositoryModule {
    @Binds
    fun initializeGameRepository(gameRepository: GameRepositoryInst): GameRepository

    @Binds
    fun initializeStoreRepository(storeRepository: StoreRepositoryInst): StoreRepository
}