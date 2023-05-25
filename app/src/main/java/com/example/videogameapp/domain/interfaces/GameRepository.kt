package com.example.videogameapp.domain.interfaces

import android.content.Context
import android.content.res.Resources
import androidx.lifecycle.LiveDataScope
import androidx.paging.PagingData
import com.example.videogameapp.domain.entity.gameentity.*
import com.example.videogameapp.domain.entity.queryentity.QueryEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    fun getGameList(scope: CoroutineScope, resources: Resources, queryGameItemEntity: QueryGameItemEntity): Flow<PagingData<GameItemEntity>>

    fun getGameDetail(id: Long): Flow<GameDetailedEntity>

    fun getGameDetailScreenshots(id: Long): Flow<List<ScreenShotEntity>>

    fun getGameStoreLink(id: Long): Flow<List<StoreEntity>>

    suspend fun insertToLibrary(gameEntity: GameItemEntity): Flow<Long>
    suspend fun getAllGameLibrary(context: Context): Flow<List<GameItemEntity>>
    suspend fun deleteGameItem(gameData: GameItemEntity): Flow<Int>
    suspend fun getDlcData(scope: CoroutineScope, id: Long): Flow<PagingData<GameItemEntity>>
    suspend fun getSpinnerPlatform(): Flow<List<QueryEntity>>

    suspend fun getSpinnerGenres(): Flow<List<QueryEntity>>
}