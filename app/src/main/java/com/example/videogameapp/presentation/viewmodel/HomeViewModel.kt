package com.example.videogameapp.presentation.viewmodel

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.videogameapp.Utils
import com.example.videogameapp.domain.entity.gameentity.*
import com.example.videogameapp.domain.entity.queryentity.QueryEntity
import com.example.videogameapp.domain.interfaces.GameUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val useCase: GameUseCase): ViewModel() {
    private val _platformSpinnerData = MutableLiveData<List<QueryEntity>>()
    private val _genresSpinnerData = MutableLiveData<List<QueryEntity>>()

    val getPlatformSpinnerData: LiveData<List<QueryEntity>> = _platformSpinnerData
    val getGenresSpinnerData: LiveData<List<QueryEntity>> = _genresSpinnerData

    private val _detailedGameData = MutableLiveData<GameDetailedEntity>()
    fun getDetailedGameData(): LiveData<GameDetailedEntity> = _detailedGameData

    private val _storeLiveData = MutableLiveData<List<StoreEntity>>()
    fun getStoreLiveData() : LiveData<List<StoreEntity>> = _storeLiveData

    private val _statusLoading = MutableLiveData<Boolean>()
    fun getStatusLoading(): LiveData<Boolean> = _statusLoading
    fun setStatusLoading(loading: Boolean) = run { _statusLoading.value = loading }

    private val _isInLibrary = MutableLiveData<Boolean>()

    init {
        getSpinnerGenres()
        getSpinnerPlatform()
    }

    fun getGameDetail(id: Long) {
        viewModelScope.launch {
            useCase.getGameDetail(id).collect {
                _detailedGameData.postValue(it)
            }
        }
    }

    fun getGameList(scope: CoroutineScope, queryGameItemEntity: QueryGameItemEntity): Flow<PagingData<GameItemEntity>> {
        return useCase.getGameList(scope, queryGameItemEntity)
    }

    fun getGameStoreLink(id: Long) {
        CoroutineScope(Dispatchers.IO).launch{
            useCase.getGameStoreLink(id).collectLatest {
                _storeLiveData.postValue(it)
            }
        }
    }

    fun getGameDetailedScreenshoot(scope: CoroutineScope, id: Long): Flow<PagingData<ScreenShotEntity>> {
        return useCase.getGameDetailScreenshots(id, scope)
    }


    private fun deleteGameItem(gameData: GameItemEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            useCase.deleteGameItem(gameData).collectLatest {
                _isInLibrary.postValue(it == -1)
            }
        }
    }
    private fun insertGameItem(gameItemEntity: GameItemEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            useCase.insertToLibrary(gameItemEntity).collectLatest {
                _isInLibrary.postValue(it != -1L)
            }
        }
    }

    suspend fun getLibraryData(): Flow<List<GameItemEntity>> {
        return useCase.getAllGameLibrary()
    }

    fun getIntent(intent: Intent): Long {
        return intent.getLongExtra(Utils.ID_KEY, -1L)
    }

    suspend fun getGameDlc(scope: CoroutineScope, id: Long): Flow<PagingData<GameItemEntity>> {
        return useCase.getDlcData(scope, id)
    }

    fun manageLibrary(gameData: GameItemEntity): LiveData<Boolean> {
        if (!gameData.isInLibrary) insertGameItem(gameData)
        else deleteGameItem(gameData)
        return _isInLibrary
    }

    suspend fun getTrailers(id: Long): Flow<List<TrailerEntity>> {
        return useCase.getTrailers(id)
    }

    private fun getSpinnerPlatform() {
        CoroutineScope(IO).launch {
            useCase.getSpinnerPlatform().collectLatest {
                _platformSpinnerData.postValue(it)
            }
        }
    }

    private fun getSpinnerGenres() {
        CoroutineScope(IO).launch {
            useCase.getSpinnerGenres().collectLatest {
                _genresSpinnerData.postValue(it)
            }
        }
    }
}