package com.example.videogameapp.domain

import androidx.paging.PagingData
import com.example.videogameapp.domain.entity.GameDetailedEntity
import com.example.videogameapp.domain.entity.GameItemEntity
import com.example.videogameapp.domain.entity.QueryGameItemEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GameUseCase @Inject constructor(private val repository: AppRepository): AppUseCase {
    override fun getGameList(scope: CoroutineScope, queryGameItemEntity: QueryGameItemEntity): Flow<PagingData<GameItemEntity>> {
        return repository.getGameList(scope, queryGameItemEntity)
    }

    override fun getGameDetail(id: Int): Flow<GameDetailedEntity> {
        return repository.getGameDetail(id)
    }

}