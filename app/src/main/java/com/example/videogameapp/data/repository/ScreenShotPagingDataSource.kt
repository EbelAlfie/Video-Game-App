package com.example.videogameapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.videogameapp.data.modeldata.gamedatamodel.ScreenShotModel
import com.example.videogameapp.data.onlineservices.GameApiService
import com.example.videogameapp.domain.entity.gameentity.ScreenShotEntity

class ScreenShotPagingDataSource (private val gameApiService: GameApiService, private val id: Long): PagingSource<Int, ScreenShotEntity>() {
    override fun getRefreshKey(state: PagingState<Int, ScreenShotEntity>): Int? {
        return null
    }

    private suspend fun getOnlineData(id: Long): List<ScreenShotEntity> {
        val response = gameApiService.getGameDetailScreenshots(id)
        return ScreenShotModel.convertList(response.screenshotList)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ScreenShotEntity> {
        val position = params.key ?: 1
        return try {
            val list = getOnlineData(id)

            LoadResult.Page (
                data = list,
                prevKey = null,
                nextKey = if (list.isEmpty()) null else position + 1
            )
        }catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}