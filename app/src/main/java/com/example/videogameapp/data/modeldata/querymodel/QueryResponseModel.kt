package com.example.videogameapp.data.modeldata.querymodel

import com.example.videogameapp.domain.entity.queryentity.QueryEntity
import com.google.gson.annotations.SerializedName

data class QueryResponseModel (
    @SerializedName("results")
    val result: List<QueryDataModel>
    )

data class QueryDataModel (
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
    ) {
    companion object {
        fun convertList (it: List<QueryDataModel>): List<QueryEntity> {
            return it.map {
                QueryEntity(it.id ?: 0, it.name ?: "")
            }
        }
    }
}