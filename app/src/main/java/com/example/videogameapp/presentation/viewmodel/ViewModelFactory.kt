package com.example.videogameapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.videogameapp.domain.interfaces.GameUseCase
import com.example.videogameapp.domain.interfaces.StoreUseCase
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val gameUseCase: GameUseCase, private val storeUseCase: StoreUseCase):ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(gameUseCase) as T
            modelClass.isAssignableFrom(StoreViewModel::class.java) -> StoreViewModel(storeUseCase, gameUseCase) as T
            modelClass.isAssignableFrom(GameDetailViewModel::class.java) -> GameDetailViewModel(gameUseCase) as T
            modelClass.isAssignableFrom(LibraryViewModel::class.java) -> LibraryViewModel(gameUseCase) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}