package com.example.videogameapp.presentation.di

import com.example.videogameapp.domain.interfaces.GameUseCase
import com.example.videogameapp.domain.GameUseCaseInst
import com.example.videogameapp.domain.StoreUseCaseInst
import com.example.videogameapp.domain.interfaces.StoreUseCase
import dagger.Binds
import dagger.Module

@Module
interface UseCaseModule {
    @Binds
    fun initializeGameUseCase(gameUseCaseInst: GameUseCaseInst): GameUseCase

    @Binds
    fun initializeStoreUseCase(storeUseCase: StoreUseCaseInst): StoreUseCase
}