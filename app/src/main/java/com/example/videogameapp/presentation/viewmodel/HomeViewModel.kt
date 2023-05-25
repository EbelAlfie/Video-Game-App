package com.example.videogameapp.presentation.viewmodel

import android.content.Context
import android.content.res.Resources
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.videogameapp.Utils
import com.example.videogameapp.domain.entity.gameentity.GameItemEntity
import com.example.videogameapp.domain.entity.gameentity.QueryGameItemEntity
import com.example.videogameapp.domain.entity.queryentity.QueryEntity
import com.example.videogameapp.domain.interfaces.GameUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val useCase: GameUseCase): ViewModel() {
    private val _platformSpinnerData = MutableLiveData<List<QueryEntity>>()
    private val _genresSpinnerData = MutableLiveData<List<QueryEntity>>()

    val getPlatformSpinnerData: LiveData<List<QueryEntity>> = _platformSpinnerData
    val getGenresSpinnerData: LiveData<List<QueryEntity>> = _genresSpinnerData
    fun getListGameData(resources: Resources): LiveData<PagingData<GameItemEntity>> = _queryParamModel.switchMap { getGameList(resources, it) }

    private val _statusLoading = MutableLiveData<Boolean>()
    fun getStatusLoading(): LiveData<Boolean> = _statusLoading
    fun setStatusLoading(loading: Boolean) { _statusLoading.value = loading }

    private val _queryParamModel = MutableLiveData<QueryGameItemEntity>()

    fun initQueryGameItemParam(search: String? = null, ordering: String? = null, dates: String? = null, platform: String? = null, store: String? = null, genres: String? = null, pageSize: Int? = null) {
        _queryParamModel.value = QueryGameItemEntity(
            search = search,
            dates = dates,
            ordering = ordering,
            platform = platform,
            store = store,
            genres = genres,
            pageSize = pageSize,
        )
    }
    private fun getGameList(resources: Resources, queryGameItemEntity: QueryGameItemEntity): LiveData<PagingData<GameItemEntity>> {
        return useCase.getGameList(viewModelScope, resources, queryGameItemEntity).asLiveData()
    }

    fun getSpinnerPlatform() {
        CoroutineScope(IO).launch {
            useCase.getSpinnerPlatform().collectLatest {
                _platformSpinnerData.postValue(it)
            }
        }
    }

    fun getSpinnerGenres() {
        CoroutineScope(IO).launch {
            useCase.getSpinnerGenres().collectLatest {
                _genresSpinnerData.postValue(it)
            }
        }
    }

    fun checkNetworkState(context: Context, queryParam: QueryGameItemEntity, loadData: (QueryGameItemEntity) -> Unit) {
        if (Utils.checkNetwork(context)){ loadData(queryParam) }
        else {
            createErrorDialog("No Network", "You appears to be offline", context, fun() { loadData(queryParam) })
            /*Utils.setUpAlertDialog("No Network", "You appears to be offline", context).apply {
                setPositiveButton(
                    "Retry"
                ) { dialogInterface, _ ->
                    dialogInterface.dismiss()
                    checkNetworkState(context, queryParam, fun(queryParam) { loadData(queryParam) })
                }.create().show()
            }*/
        }
    }

    fun createErrorDialog(title: String, message: String, context: Context, execute: () -> Unit) {
        Utils.setUpAlertDialog(title, message, context).apply {
            setPositiveButton("Retry") { dialog, _ ->
                dialog.dismiss()
                execute()
            }
        }.show()
    }
}