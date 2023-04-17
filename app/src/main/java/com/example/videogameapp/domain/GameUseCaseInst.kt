package com.example.videogameapp.domain

import androidx.paging.PagingData
import com.example.videogameapp.domain.entity.gameentity.*
import com.example.videogameapp.domain.interfaces.GameRepository
import com.example.videogameapp.domain.interfaces.GameUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GameUseCaseInst @Inject constructor(private val repository: GameRepository): GameUseCase {
    override fun getGameList(scope: CoroutineScope, queryGameItemEntity: QueryGameItemEntity): Flow<PagingData<GameItemEntity>> {
        return repository.getGameList(scope, queryGameItemEntity)
    }

    override fun getGameDetail(id: Long): Flow<GameDetailedEntity> {
        return repository.getGameDetail(id)
    }

    override fun getGameDetailScreenshots(
        id: Long,
        scope: CoroutineScope
    ): Flow<PagingData<ScreenShotEntity>> {
        return repository.getGameDetailScreenshots(id, scope)
    }

    override fun getGameStoreLink(id: Long): Flow<List<StoreEntity>> {
        return repository.getGameStoreLink(id)
    }

    override suspend fun insertToLibrary(gameEntity: GameItemEntity) {
        repository.insertToLibrary(gameEntity)
    }

    override suspend fun getAllGameLibrary(): Flow<List<GameItemEntity>> {
        return repository.getAllGameLibrary()
    }

}