package com.example.videogameapp.data.di

import android.content.Context
import com.example.videogameapp.domain.AppRepository
import com.example.videogameapp.domain.AppUseCase
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

    fun initializeRepository(): AppRepository
}