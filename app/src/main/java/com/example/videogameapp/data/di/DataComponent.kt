package com.example.videogameapp.data.di

import android.content.Context
import com.example.videogameapp.domain.interfaces.GameRepository
import com.example.videogameapp.domain.interfaces.StoreRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [RepositoryModule::class]
)
interface DataComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): DataComponent
    }

    fun initializeGameRepository(): GameRepository
    fun initializeStoreRepository(): StoreRepository
}