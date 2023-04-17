package com.example.videogameapp.data.modeldata.gamedatamodel

import com.example.videogameapp.domain.entity.gameentity.StoreEntity
import com.google.gson.annotations.SerializedName

data class GameStoreResponse (
    @SerializedName("results")
    val storeLinkList: List<GameStoreModel>
    )

data class GameStoreModel(
    @SerializedName("store_id")
    val storeId: Int?,
    @SerializedName("url")
    val url: String?
) {
    companion object {
        fun convertList(stores: List<GameStoreModel?>): List<StoreEntity> {
            return stores.map {
                StoreEntity(it?.storeId ?: 0, "", it?.url ?: "")
            }
        }
    }
}
