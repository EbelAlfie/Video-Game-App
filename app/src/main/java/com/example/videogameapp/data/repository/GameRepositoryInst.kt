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
import com.example.videogameapp.data.onlineservices.GameApiService
import com.example.videogameapp.domain.entity.gameentity.*
import com.example.videogameapp.domain.interfaces.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.internal.NopCollector.emit
import javax.inject.Inject

class GameRepositoryInst @Inject constructor(private val gameApiService: GameApiService, private val libraryDbObj : LocalDbModule):
    GameRepository {
    override fun getGameList(scope: CoroutineScope, queryGameItemEntity: QueryGameItemEntity): Flow<PagingData<GameItemEntity>> {
        return Pager(config = PagingConfig(
            pageSize = 10
        )) {
            GamePagingDataSource(gameApiService, QueryGameItemEntity.transform(queryGameItemEntity))
        }.flow.cachedIn(scope)
    }

    override fun getGameDetail(id: Long): Flow<GameDetailedEntity> {
        return flow{
            try {
                val data = gameApiService.getGameDetail(id)
                emit(GameDetailedModel.convert(data))
            }catch (e: Exception) {
                Log.d("TAG", e.message.toString())
                emit(GameDetailedEntity(0, "", "", false, "", "", null, 0, "", "", listOf(),listOf(),listOf(), listOf(), listOf(), listOf(), listOf()))
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

    override suspend fun insertToLibrary(gameEntity: GameItemEntity) {
        gameEntity.apply {
            val gameData = GameItemEntity.transformDbModel(this)
            val platformData = PlatformEntity.toPlatformModel(id, platforms)
            val ratingData = RatingEntity.toRatingModel(id, ratings)
            val genresData = GenresEntity.toGenreModel(id, genres)
            try {
                libraryDbObj.gameItemDao().insertGameItem(gameData)
            }catch (e : Exception) {
                Log.d("Error", e.message.toString())
            }
//            libraryDbObj.gameItemDao().insertGameItemPlatform(platformData)
//            libraryDbObj.gameItemDao().insertGameItemRating(ratingData)
//            libraryDbObj.gameItemDao().insertGameItemGenre(genresData)
        }
    }

    override suspend fun getAllGameLibrary(): Flow<List<GameItemEntity>> {
        val data = libraryDbObj.gameItemDao().getAllGameLibrary()
        return flow {
            GameItemDbModel.convertList(data)
        }
    }


}