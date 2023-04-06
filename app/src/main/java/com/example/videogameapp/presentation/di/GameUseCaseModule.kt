package com.example.videogameapp.presentation.di

import com.example.videogameapp.domain.AppUseCase
import com.example.videogameapp.domain.GameUseCase
import dagger.Binds
import dagger.Module

@Module
interface GameUseCaseModule {
    @Binds
    fun initializeGameUseCase(gameUseCase: GameUseCase): AppUseCase
}