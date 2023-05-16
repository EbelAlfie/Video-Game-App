package com.example.videogameapp.domain.entity.gameentity

import com.example.videogameapp.data.modeldata.gamedatamodel.VideoData
import com.google.gson.annotations.SerializedName

data class DeveloperDetailEntity (
    @SerializedName("name")
    val name: String
    )

data class PublisherDetailEntity (
    @SerializedName("name")
    val publisher: String
    )

data class TagsEntity (
    val tagId: Int,
    val tagName: String
    )

data class StoreEntity (
    val id: Int,
    var name: String,
    val url: String
    )

data class TrailerEntity (
    val id: Long,
    val name: String,
    val previewImage: String,
    val data: String
    )