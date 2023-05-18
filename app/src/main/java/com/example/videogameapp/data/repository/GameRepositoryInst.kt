package com.example.videogameapp.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.videogameapp.data.di.LocalDbModule
import com.example.videogameapp.data.modeldata.databasemodel.GameItemDbModel
import com.example.videogameapp.data.modeldata.gamedatamodel.GameDetailedModel
import com.example.videogameapp.data.modeldata.gamedatamodel.GameStoreModel
import com.example.videogameapp.data.modeldata.gamedatamodel.TrailerModel
import com.example.videogameapp.data.modeldata.querymodel.QueryDataModel
import com.example.videogameapp.data.onlineservices.GameApiService
import com.example.videogameapp.data.onlineservices.ServiceUtils.ORDER_POPULAR
import com.example.videogameapp.data.onlineservices.ServiceUtils.SPINNER_PAGE
import com.example.videogameapp.data.onlineservices.ServiceUtils.SPINNER_SIZE
import com.example.videogameapp.domain.entity.gameentity.*
import com.example.videogameapp.domain.entity.queryentity.QueryEntity
import com.example.videogameapp.domain.interfaces.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GameRepositoryInst @Inject constructor(private val gameApiService: GameApiService, private val libraryDbObj : LocalDbModule):
    GameRepository {
    override fun getGameList(scope: CoroutineScope, queryGameItemEntity: QueryGameItemEntity): Flow<PagingData<GameItemEntity>> {
        return Pager(config = PagingConfig(
            pageSize = 10
        )) {
            GamePagingDataSource(libraryDbObj, gameApiService, QueryGameItemEntity.transform(queryGameItemEntity))
        }.flow.cachedIn(scope)
    }

    override fun getGameDetail(id: Long): Flow<GameDetailedEntity> {
        return flow{
            try {
                val data = gameApiService.getGameDetail(id)
                val libraryStatus = libraryDbObj.gameItemDao().getSpecificGame(data.id ?: -1)
                data.isInLibrary = (libraryStatus != null)
                emit(GameDetailedModel.convert(data))
            }catch (e: Exception) {
                Log.d("TAG", e.message.toString())
                emit(GameDetailedEntity(0, "", "", false, "", "", null, 0, "", "", listOf(), listOf(),listOf(),listOf(), listOf(), listOf(), listOf(), listOf(), false))
            }
        }
    }

    override fun getGameDetailScreenshots(id: Long, scope: CoroutineScope): Flow<PagingData<ScreenShotEntity>> {
        return Pager(config = PagingConfig(
            pageSize = 10
        )) {
            ScreenShotPagingDataSource(gameApiService, id)
        }.flow.cachedIn(scope)
    }

    override fun getGameStoreLink(id: Long): Flow<List<StoreEntity>> {
        return flow{
            try {
                val data = gameApiService.getGameStoreLink(id)
                emit(GameStoreModel.convertList(data.storeLinkList))
            }catch (e: Exception) {
                Log.d("TAG", e.message.toString())
                emit(listOf())
            }
        }
    }

    override suspend fun insertToLibrary(gameEntity: GameItemEntity): Flow<Long> {
        return flow{
            try {
                gameEntity.apply {
                    val gameData = GameItemEntity.transformDbModel(this)
                    val response = libraryDbObj.gameItemDao().insertGameItem(gameData)
                    emit(response)
                }
            }catch (e: Exception) {
                Log.d("test", e.message.toString())
                emit(-1)
            }
        }.flowOn(IO)
    }

    override suspend fun getAllGameLibrary(): Flow<List<GameItemEntity>> {
        return flow{
            try {
                val data = libraryDbObj.gameItemDao().getAllGameLibrary()
                emit(GameItemDbModel.convertList(data))
            }catch (e: Exception) {
                Log.d("TAG", e.message.toString())
                emit(listOf())
            }
        }.flowOn(IO)
    }

    override suspend fun deleteGameItem(gameData: GameItemEntity): Flow<Int> {
        return flow {
            try {
                val data = GameItemEntity.transformDbModel(gameData)
                val response = libraryDbObj.gameItemDao().deleteGameItem(data)
                emit(response)
            }catch (e : Exception) {
                Log.d("ErrDel", e.toString())
                emit(0)
            }
        }.flowOn(IO)
    }

    override suspend fun getDlcData(scope: CoroutineScope, id: Long): Flow<PagingData<GameItemEntity>> {
        return Pager(config = PagingConfig(
            pageSize = 10
        )) {
            DlcPagingDataSource(id, gameApiService)
        }.flow.cachedIn(scope)
    }

    override suspend fun getTrailers(id: Long): Flow<List<TrailerEntity>> {
        return flow {
            try{
                val response = gameApiService.getTrailers(id)
                emit(TrailerModel.convert(response.results))
            }catch (e: Exception) {
                emit(listOf())
            }
        }
    }

    override suspend fun getSpinnerPlatform(): Flow<List<QueryEntity>> {
        return flow {
            try {
                val response = gameApiService.getSpinnerPlatform(ORDER_POPULAR, SPINNER_PAGE, SPINNER_SIZE)
                emit(QueryDataModel.convertList(response.result))
            }catch (e: Exception) {
                emit(listOf())
            }
        }
    }

    override suspend fun getSpinnerGenres(): Flow<List<QueryEntity>> {
        return flow {
            try {
                val response = gameApiService.getSpinnerGenres(ORDER_POPULAR, SPINNER_PAGE, SPINNER_SIZE)
                emit(QueryDataModel.convertList(response.result))
            }catch (e: Exception) {
                emit(listOf())
            }
        }
    }
}