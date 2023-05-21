package com.example.videogameapp.presentation.viewmodel

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.videogameapp.Utils
import com.example.videogameapp.domain.entity.gameentity.GameItemEntity
import com.example.videogameapp.domain.entity.gameentity.QueryGameItemEntity
import com.example.videogameapp.domain.entity.storeentity.StoreDetailEntity
import com.example.videogameapp.domain.entity.storeentity.StoreItemEntity
import com.example.videogameapp.domain.interfaces.GameUseCase
import com.example.videogameapp.domain.interfaces.StoreUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class StoreViewModel @Inject constructor(private val storeUseCase: StoreUseCase, private val gameUseCase: GameUseCase) : ViewModel() {
    private val _storeDetailData = MutableLiveData<StoreDetailEntity>()
    fun getStoreDetailData() : LiveData<StoreDetailEntity> = _storeDetailData

    private val _statusLoading = MutableLiveData<Boolean>()
    fun getStatusLoading(): LiveData<Boolean> = _statusLoading
    fun setStatusLoading(loading: Boolean) = run { _statusLoading.value = loading }

    fun getAllStore(scope: CoroutineScope): Flow<List<StoreItemEntity>> {
        return storeUseCase.getAllStore(scope)
    }

    fun getAllGameByStore(queryGameItemEntity: QueryGameItemEntity): Flow<PagingData<GameItemEntity>> {
        return gameUseCase.getGameList(queryGameItemEntity)
    }

    fun getStoreId(intent: Intent): Long {
        return intent.getLongExtra(Utils.ID_KEY, -1L)
    }

    fun getDetailedStoreData(id: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            storeUseCase.getDetailedStoreData(id).collectLatest {
                _storeDetailData.postValue(it)
            }
        }
    }
}