package com.example.videogameapp.data.modeldata.storedatamodel

import com.example.videogameapp.domain.entity.storeentity.StoreDetailEntity
import com.google.gson.annotations.SerializedName

data class StoreDetailModel (
    @SerializedName("description")
    val desc: String?
    ){
    companion object {
        fun convert(it: List<StoreDetailModel?>) : List<StoreDetailEntity> {
            return it.map {
                convertItem(it ?: StoreDetailModel(""))
            }
        }
        fun convertItem(it: StoreDetailModel): StoreDetailEntity {
            return StoreDetailEntity(it.desc ?: "")
        }
    }
}