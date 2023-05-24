package com.example.videogameapp.presentation.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.videogameapp.Utils
import com.example.videogameapp.domain.entity.gameentity.*
import com.example.videogameapp.domain.interfaces.GameUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class GameDetailViewModel @Inject constructor(private val useCase: GameUseCase): ViewModel() {
    private val _detailedGameData = MutableLiveData<GameDetailedEntity>()
    fun getDetailedGameData(): LiveData<GameDetailedEntity> = _detailedGameData

    private val _storeLiveData = MutableLiveData<List<StoreEntity>>()
    fun getStoreLiveData() : LiveData<List<StoreEntity>> = _storeLiveData

    private val _statusLoading = MutableLiveData<Boolean>(false)
    fun getStatusLoading(): LiveData<Boolean> = _statusLoading
    fun setStatusLoading(loading: Boolean) { _statusLoading.value = loading }

    private val _isInLibrary = MutableLiveData<Boolean>()

    fun getGameDetail(id: Long) {
        viewModelScope.launch {
            useCase.getGameDetail(id).collect {
                _detailedGameData.postValue(it)
            }
        }
    }

    fun getGameStoreLink(id: Long) {
        CoroutineScope(Dispatchers.IO).launch{
            useCase.getGameStoreLink(id).collectLatest {
                _storeLiveData.postValue(it)
            }
        }
    }

    fun getGameDetailedScreenshoot(id: Long): Flow<List<ScreenShotEntity>> {
        return useCase.getGameDetailScreenshots(id)
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

    fun getLibraryStatus(): LiveData<Boolean> = _isInLibrary

    suspend fun getTrailers(id: Long): Flow<List<TrailerEntity>> {
        return useCase.getTrailers(id)
    }

    fun checkNetworkState(context: Context, id: Long, loadData: (Long) -> Unit) {
        if (Utils.checkNetwork(context)){ loadData(id) }
        else {
            Utils.setUpAlertDialog("No Network", "You appears to be offline", context).apply {
                setPositiveButton(
                    "Retry"
                ) { dialogInterface, _ ->
                    dialogInterface.dismiss()
                    checkNetworkState(context, id, fun(id) { loadData(id) })
                }.create().show()
            }
        }
    }

}