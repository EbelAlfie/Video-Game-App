package com.example.videogameapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.videogameapp.data.modeldata.GameItemModel
import com.example.videogameapp.data.modeldata.QueryGameItemModel
import com.example.videogameapp.data.onlineservices.ApiService
import com.example.videogameapp.domain.entity.GameItemEntity
import com.example.videogameapp.domain.entity.QueryGameItemEntity

class GamePagingDataSource(private val apiService: ApiService, private val queryGameItemModel: QueryGameItemModel): PagingSource<Int, GameItemEntity>() {
    override fun getRefreshKey(state: PagingState<Int, GameItemEntity>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GameItemEntity> {
        val position = params.key ?: 1
        return try {
            val list = getOnlineData(position)
            LoadResult.Page(
                data = list,
                nextKey = if (list.isEmpty()) null else position + 1,
                prevKey = null
            )
        }catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private suspend fun getOnlineData(position: Int): List<GameItemEntity> {
        val response = apiService.getAllGame(
            dates = queryGameItemModel.dates,
            search = queryGameItemModel.search,
            page = if (position == 1) 1 else position * 10 - 10,
        )
        return GameItemModel.convertList(response.results)
    }

}