package com.example.videogameapp.data.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.util.query
import com.example.videogameapp.data.di.LocalDbModule
import com.example.videogameapp.data.modeldata.gamedatamodel.GameItemModel
import com.example.videogameapp.data.modeldata.gamedatamodel.QueryGameItemModel
import com.example.videogameapp.data.onlineservices.GameApiService
import com.example.videogameapp.domain.entity.gameentity.GameItemEntity

class GamePagingDataSource(private val libraryDbObj: LocalDbModule, private val gameApiService: GameApiService, private val queryGameItemModel: QueryGameItemModel): PagingSource<Int, GameItemEntity>() {
    override fun getRefreshKey(state: PagingState<Int, GameItemEntity>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GameItemEntity> {
        val position = params.key ?: 1
        return try {
            val responseEntity = getOnlineData(position)
            val list = responseEntity.third
            val next = responseEntity.first
            //val prev = responseEntity.second
            LoadResult.Page(
                data = list,
                nextKey = if (next == null || list.isEmpty() || queryGameItemModel.pageSize == 5) null else position + 1,
                prevKey = null /*if (prev == null || list.isEmpty() || queryGameItemModel.pageSize == 5) null else position - 1*/
            )
        }catch (e: Exception) {
            Log.d("ERROR", e.toString())
            LoadResult.Error(e)
        }
    }

    private suspend fun getOnlineData(position: Int): Triple<String?, String?, List<GameItemEntity>> {
        val response = gameApiService.getAllGame(
            dates = queryGameItemModel.dates,
            search = queryGameItemModel.search,
            store = queryGameItemModel.store,
            platform = queryGameItemModel.platform,
            genres = queryGameItemModel.genres,
            ordering = queryGameItemModel.ordering,
            page = if (position == 1 || queryGameItemModel.pageSize == 5) 1 else position + 1,
            pageSize = queryGameItemModel.pageSize
        )

        response.results.forEachIndexed { index, _ ->
            val data = libraryDbObj.gameItemDao().getSpecificGame(response.results[index].id ?: -1)
            response.results[index].isInLibrary = data != null
        }

        return Triple(response.next, response.prev, GameItemModel.convertList(response.results))
    }

}