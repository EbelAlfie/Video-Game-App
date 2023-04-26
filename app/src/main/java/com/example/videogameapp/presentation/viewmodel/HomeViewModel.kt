package com.example.videogameapp.presentation.viewmodel

import android.content.Intent
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.videogameapp.Utils
import com.example.videogameapp.domain.interfaces.GameUseCase
import com.example.videogameapp.domain.entity.gameentity.GameDetailedEntity
import com.example.videogameapp.domain.entity.gameentity.GameItemEntity
import com.example.videogameapp.domain.entity.gameentity.QueryGameItemEntity
import com.example.videogameapp.domain.entity.gameentity.StoreEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val useCase: GameUseCase): ViewModel() {
    private val _detailedGameData = MutableLiveData<GameDetailedEntity>()
    fun getDetailedGameData(): LiveData<GameDetailedEntity> = _detailedGameData

    private val _storeLiveData = MutableLiveData<List<StoreEntity>>()
    fun getStoreLiveData() : LiveData<List<StoreEntity>> = _storeLiveData

    private val _statusLoading = MutableLiveData<Boolean>()
    fun getStatusLoading(): LiveData<Boolean> = _statusLoading
    fun setStatusLoading(loading: Boolean) = run { _statusLoading.value = loading }

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

    fun insertGameItem(gameItemEntity: GameItemEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            useCase.insertToLibrary(gameItemEntity)
        }
    }

    suspend fun getLibraryData(): Flow<List<GameItemEntity>> {
        return useCase.getAllGameLibrary()
    }

    fun getIntent(intent: Intent): GameItemEntity? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Utils.OBJ_KEY, GameItemEntity::class.java)
        } else { intent.getParcelableExtra(Utils.OBJ_KEY) }
    }
}