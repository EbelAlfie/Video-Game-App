package com.example.videogameapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.videogameapp.data.modeldata.gamedatamodel.GameItemModel
import com.example.videogameapp.data.onlineservices.GameApiService
import com.example.videogameapp.domain.entity.gameentity.GameItemEntity

class DlcPagingDataSource(private val id: Long, private val apiService: GameApiService): PagingSource<Int, GameItemEntity>() {
    override fun getRefreshKey(state: PagingState<Int, GameItemEntity>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GameItemEntity> {
        val position = params.key ?: 1

        return try {
            val response = apiService.getGameDlc(id)
            val data = GameItemModel.convertList(response.dlc)
            LoadResult.Page (
                data = data,
                nextKey = if (data.isEmpty() || response.next.isNullOrEmpty()) null else position + 1,
                prevKey = null
            )
        } catch(e: Exception) {
            LoadResult.Error(e)
        }
    }
}