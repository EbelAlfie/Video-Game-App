package com.example.videogameapp.data.repository

import com.example.videogameapp.data.modeldata.storedatamodel.StoreDetailModel
import com.example.videogameapp.data.modeldata.storedatamodel.StoreItemModel
import com.example.videogameapp.data.onlineservices.StoreApiService
import com.example.videogameapp.domain.entity.storeentity.StoreDetailEntity
import com.example.videogameapp.domain.entity.storeentity.StoreItemEntity
import com.example.videogameapp.domain.interfaces.StoreRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StoreRepositoryInst @Inject constructor(private val storeApiService: StoreApiService) : StoreRepository {
    override fun getAllStore(scope: CoroutineScope): Flow<List<StoreItemEntity>> {
        return flow{
            try {
                val response = storeApiService.getAllStore()
                emit(StoreItemModel.convertList(response.stores))
            }catch (e: Exception) {
                emit(listOf())
            }
        }
    }

    override suspend fun getDetailedStoreData(id: Long): Flow<StoreDetailEntity> {
        return flow{ try {
                val response = storeApiService.getDetailedStoreData(id)
                emit(StoreDetailModel.convertItem(response))
            }catch (e: Exception) {
                emit(StoreDetailEntity(""))
            }
        }
    }

}