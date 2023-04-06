package com.example.videogameapp

import android.app.Application
import com.example.videogameapp.data.di.DaggerDataComponent
import com.example.videogameapp.data.di.DataComponent
import com.example.videogameapp.presentation.di.AppComponent
import com.example.videogameapp.presentation.di.DaggerAppComponent

class RawgApp: Application() {
    private val dataComponent: DataComponent by lazy {
        DaggerDataComponent.factory().create(applicationContext)
    }
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(dataComponent)
    }
}
