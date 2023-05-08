package com.example.videogameapp.domain.interfaces

import androidx.paging.PagingData
import com.example.videogameapp.domain.entity.gameentity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface GameUseCase {
    fun getGameList(scope: CoroutineScope, queryGameItemEntity: QueryGameItemEntity): Flow<PagingData<GameItemEntity>>

    fun getGameDetail(id: Long): Flow<GameDetailedEntity>

    fun getGameDetailScreenshots(id: Long, scope: CoroutineScope): Flow<PagingData<ScreenShotEntity>>

    fun getGameStoreLink(id: Long): Flow<List<StoreEntity>>

    suspend fun insertToLibrary(gameEntity: GameItemEntity): Flow<Long>

    suspend fun getAllGameLibrary(): Flow<List<GameItemEntity>>
    suspend fun deleteGameItem(gameData: GameItemEntity): Flow<Int>
    suspend fun getDlcData(scope: CoroutineScope, id: Long): Flow<PagingData<GameItemEntity>>
}