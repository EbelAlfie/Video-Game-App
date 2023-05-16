package com.example.videogameapp.data.modeldata.gamedatamodel

import com.example.videogameapp.domain.entity.gameentity.TrailerEntity
import com.google.gson.annotations.SerializedName

data class TrailerResponseModel(
    @SerializedName("results")
    val results: List<TrailerModel>
)

data class VideoData (
    @SerializedName("480")
    val videoUrl: String?
    )

data class TrailerModel(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("preview")
    val previewImage: String?,
    @SerializedName("data")
    val data: VideoData
) {
    companion object {
        fun convert(it: List<TrailerModel>): List<TrailerEntity> {
            return it.map {
                TrailerEntity(it.id ?: -1, it.name ?: "", it.previewImage ?: "", it.data.videoUrl ?: "")
            }
        }
    }
}
