package com.example.videogameapp.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.videogameapp.data.modeldata.GameDetailedModel
import com.example.videogameapp.data.onlineservices.ApiService
import com.example.videogameapp.domain.AppRepository
import com.example.videogameapp.domain.entity.GameDetailedEntity
import com.example.videogameapp.domain.entity.GameItemEntity
import com.example.videogameapp.domain.entity.QueryGameItemEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GameRepository @Inject constructor(private val apiService: ApiService): AppRepository {
    override fun getGameList(scope: CoroutineScope, queryGameItemEntity: QueryGameItemEntity): Flow<PagingData<GameItemEntity>> {
        return Pager(config = PagingConfig(
            pageSize = 10
        )) {
            GamePagingDataSource(apiService, QueryGameItemEntity.transform(queryGameItemEntity))
        }.flow.cachedIn(scope)
    }

    override fun getGameDetail(id: Long): Flow<GameDetailedEntity> {
        return flow{
            try {
                val data = apiService.getGameDetail(id)
                emit(GameDetailedModel.convert(data))
            }catch (e: Exception) {
                Log.d("TAG", e.message.toString())
                emit(GameDetailedEntity(0, "", "", false, "", "", "", null, 0, "", listOf(), listOf(),listOf(),listOf()))
            }

        }
    }

}