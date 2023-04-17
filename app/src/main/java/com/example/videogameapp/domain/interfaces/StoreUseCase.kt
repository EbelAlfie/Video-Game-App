package com.example.videogameapp.domain.interfaces

import com.example.videogameapp.domain.entity.storeentity.StoreDetailEntity
import com.example.videogameapp.domain.entity.storeentity.StoreItemEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface StoreUseCase {

    fun getAllStore(scope: CoroutineScope): Flow<List<StoreItemEntity>>

    suspend fun getDetailedStoreData(id: Long): Flow<StoreDetailEntity>
}