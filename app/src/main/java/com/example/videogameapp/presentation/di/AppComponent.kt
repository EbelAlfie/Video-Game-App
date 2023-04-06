package com.example.videogameapp.presentation.di

import com.example.videogameapp.data.di.DataComponent
import com.example.videogameapp.presentation.view.MainActivity
import dagger.Component

@RawgScope
@Component(
    modules = [GameUseCaseModule::class],
    dependencies = [DataComponent::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(dataComponent: DataComponent): AppComponent
    }

    fun injectMain(mainActivity: MainActivity)

}