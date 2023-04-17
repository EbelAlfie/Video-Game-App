package com.example.videogameapp.domain

import com.example.videogameapp.domain.entity.storeentity.StoreDetailEntity
import com.example.videogameapp.domain.entity.storeentity.StoreItemEntity
import com.example.videogameapp.domain.interfaces.StoreRepository
import com.example.videogameapp.domain.interfaces.StoreUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StoreUseCaseInst @Inject constructor(private val repository: StoreRepository) : StoreUseCase {
    override fun getAllStore(scope: CoroutineScope): Flow<List<StoreItemEntity>> {
        return repository.getAllStore(scope)
    }

    override suspend fun getDetailedStoreData(id: Long): Flow<StoreDetailEntity> {
        return repository.getDetailedStoreData(id)
    }

}