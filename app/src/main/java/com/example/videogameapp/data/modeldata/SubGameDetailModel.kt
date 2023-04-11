package com.example.videogameapp.data.modeldata

import com.example.videogameapp.domain.entity.DeveloperDetailEntity
import com.example.videogameapp.domain.entity.PublisherDetailEntity
import com.google.gson.annotations.SerializedName

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