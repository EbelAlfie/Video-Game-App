package com.example.videogameapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.videogameapp.domain.AppUseCase
import com.example.videogameapp.domain.entity.GameDetailedEntity
import com.example.videogameapp.domain.entity.GameItemEntity
import com.example.videogameapp.domain.entity.QueryGameItemEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val useCase: AppUseCase): ViewModel() {
    private val _detailedGameData = MutableLiveData<GameDetailedEntity>()
    fun getDetailedGameData(): LiveData<GameDetailedEntity> = _detailedGameData

    fun getGameDetail(id: Int) {

    }

    fun getGameList(scope: CoroutineScope, queryGameItemEntity: QueryGameItemEntity): Flow<PagingData<GameItemEntity>> {
        return useCase.getGameList(scope, queryGameItemEntity)
    }
}