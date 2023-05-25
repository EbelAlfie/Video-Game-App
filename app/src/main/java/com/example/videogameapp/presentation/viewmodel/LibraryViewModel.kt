package com.example.videogameapp.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.videogameapp.domain.entity.gameentity.GameItemEntity
import com.example.videogameapp.domain.interfaces.GameUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class LibraryViewModel @Inject constructor(private val useCase: GameUseCase): ViewModel() {
    private val _statusLoading = MutableLiveData<Boolean>()
    fun getStatusLoading(): LiveData<Boolean> = _statusLoading
    fun setStatusLoading(loading: Boolean) { _statusLoading.value = loading }

    private val _isInLibrary = MutableLiveData<Boolean>()

    val getLibraryStatus: LiveData<Boolean> = _isInLibrary

    private fun deleteGameItem(gameData: GameItemEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            useCase.deleteGameItem(gameData).collectLatest {
                _isInLibrary.postValue(it == -1)
            }
        }
    }

    suspend fun getLibraryData(context: Context): Flow<List<GameItemEntity>> {
        return useCase.getAllGameLibrary(context)
    }

    fun manageLibrary(gameData: GameItemEntity) {
        deleteGameItem(gameData)
    }
}