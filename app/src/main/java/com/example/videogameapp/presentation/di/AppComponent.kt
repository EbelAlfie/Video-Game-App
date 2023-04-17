package com.example.videogameapp.presentation.di

import com.example.videogameapp.data.di.DataComponent
import com.example.videogameapp.presentation.view.homeview.GameDetailActivity
import com.example.videogameapp.presentation.view.MainActivity
import com.example.videogameapp.presentation.view.storeview.StoreDetailActivity
import dagger.Component

@RawgScope
@Component(
    modules = [UseCaseModule::class],
    dependencies = [DataComponent::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(dataComponent: DataComponent): AppComponent
    }

    fun injectMain(mainActivity: MainActivity)
    fun injectDetailed(gameDetailActivity: GameDetailActivity)
    fun injectStoreDetail(storeDetailActivity: StoreDetailActivity)
}