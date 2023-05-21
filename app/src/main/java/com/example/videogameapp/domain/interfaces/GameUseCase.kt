package com.example.videogameapp.domain.interfaces

import androidx.lifecycle.LiveDataScope
import androidx.paging.PagingData
import com.example.videogameapp.domain.entity.gameentity.*
import com.example.videogameapp.domain.entity.queryentity.QueryEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface GameUseCase {
    fun getGameList(queryGameItemEntity: QueryGameItemEntity): Flow<PagingData<GameItemEntity>>

    fun getGameDetail(id: Long): Flow<GameDetailedEntity>

    fun getGameDetailScreenshots(id: Long, scope: CoroutineScope): Flow<PagingData<ScreenShotEntity>>

    fun getGameStoreLink(id: Long): Flow<List<StoreEntity>>

    suspend fun insertToLibrary(gameEntity: GameItemEntity): Flow<Long>

    suspend fun getAllGameLibrary(): Flow<List<GameItemEntity>>
    suspend fun deleteGameItem(gameData: GameItemEntity): Flow<Int>
    suspend fun getDlcData(scope: CoroutineScope, id: Long): Flow<PagingData<GameItemEntity>>
    suspend fun getTrailers(id: Long): Flow<List<TrailerEntity>>
    suspend fun getSpinnerPlatform(): Flow<List<QueryEntity>>

    suspend fun getSpinnerGenres(): Flow<List<QueryEntity>>
}