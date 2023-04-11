package com.example.videogameapp.domain

import androidx.paging.PagingData
import com.example.videogameapp.domain.entity.GameDetailedEntity
import com.example.videogameapp.domain.entity.GameItemEntity
import com.example.videogameapp.domain.entity.QueryGameItemEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface AppUseCase {
    fun getGameList(scope: CoroutineScope, queryGameItemEntity: QueryGameItemEntity): Flow<PagingData<GameItemEntity>>

    fun getGameDetail(id: Long): Flow<GameDetailedEntity>
}