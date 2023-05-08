package com.example.videogameapp.data.modeldata.gamedatamodel

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
)
