package com.example.videogameapp.data.modeldata.gamedatamodel

import com.example.videogameapp.domain.entity.gameentity.DeveloperDetailEntity
import com.example.videogameapp.domain.entity.gameentity.PublisherDetailEntity
import com.example.videogameapp.domain.entity.gameentity.TagsEntity
import com.google.gson.annotations.SerializedName

data class ScreenshotDetailResponseModel(
    @SerializedName("results")
    val screenshotList: List<ScreenShotModel>
)

data class DeveloperDetailModel (
    @SerializedName("name")
    val name: String?
    ) {
    companion object {
        fun convertList(devList: List<DeveloperDetailModel?>): List<DeveloperDetailEntity> {
            return devList.map {
                DeveloperDetailEntity(it?.name ?: "")
            }
        }
    }
}

data class PublisherDetailModel (
    @SerializedName("name")
    val publisher: String?
    ) {
    companion object {
        fun convertList(publisherList: List<PublisherDetailModel?>): List<PublisherDetailEntity> {
            return publisherList.map {
                PublisherDetailEntity(it?.publisher ?: "")
            }
        }
    }
}

data class AgeRatingModel (
    @SerializedName("name")
    val ageRating: String
    )

data class TagsModel (
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
    ) {
    companion object {
        fun convertList(tags: List<TagsModel?>): List<TagsEntity> {
            return tags.map {
                TagsEntity(it?.id ?: 0, it?.name ?: "")
            }
        }
    }
}