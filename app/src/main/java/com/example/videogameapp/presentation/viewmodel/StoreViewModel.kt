package com.example.videogameapp.presentation.viewmodel

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import androidx.lifecycle.*
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

    fun getListGameData(resources: Resources): LiveData<PagingData<GameItemEntity>> = _queryParamModel.switchMap { getGameList(resources, it) }
    private val _queryParamModel = MutableLiveData<QueryGameItemEntity>()

    private val _statusLoading = MutableLiveData(false)
    fun getStatusLoading(): LiveData<Boolean> = _statusLoading
    fun setStatusLoading(loading: Boolean) { _statusLoading.value = loading }

    private val _pagingItemCount = MutableLiveData<Int>()
    val getPagingItemCount: LiveData<Int> = _pagingItemCount
    fun setItemCount(itemCount: Int) { _pagingItemCount.value = itemCount }

    private fun getGameList(resources: Resources, queryGameItemEntity: QueryGameItemEntity): LiveData<PagingData<GameItemEntity>> {
        return gameUseCase.getGameList(viewModelScope, resources, queryGameItemEntity).asLiveData()
    }

    fun initQueryGameItemParam(search: String? = null, ordering: String? = null, dates: String? = null, platform: String? = null, store: String? = null, genres: String? = null, pageSize: Int? = null) {
        setStatusLoading(true)
        _queryParamModel.value = QueryGameItemEntity(
            search = search,
            dates = dates,
            ordering = ordering,
            platform = platform,
            store = store,
            genres = genres,
            pageSize = pageSize
        )
    }

    fun getAllStore(scope: CoroutineScope): Flow<List<StoreItemEntity>> {
        return storeUseCase.getAllStore(scope)
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

    fun checkNetworkState(context: Context, loadData: () -> Unit) {
        if (Utils.checkNetwork(context)){ loadData() }
        else {
            Utils.createErrorDialog(
                "No Network",
                "You appears to be offline",
                context,
                fun() {
                    checkNetworkState(context, loadData)
                }
            )
        }
    }
}