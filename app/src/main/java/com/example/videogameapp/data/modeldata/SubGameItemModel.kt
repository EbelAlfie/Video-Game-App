package com.example.videogameapp.data.modeldata

import com.example.videogameapp.domain.entity.GenresEntity
import com.example.videogameapp.domain.entity.PlatformEntity
import com.example.videogameapp.domain.entity.RatingEntity
import com.example.videogameapp.domain.entity.ScreenShotEntity
import com.google.gson.annotations.SerializedName

data class StoreResponseModel (
    @SerializedName("store")
    val store: StoreModel
    )

data class RatingModel (
    @SerializedName("title")
    val ratingTitle: String?,
    @SerializedName("count")
    val ratingCount: Int?,
    @SerializedName("percentage")
    val percentage: Int?
    ) {
    companion object {
        fun convertList(ratingModelList: List<RatingModel?>) : List<RatingEntity> {
            return ratingModelList.map {
                RatingEntity(it?.ratingTitle ?: "", it?.ratingCount ?: 0, it?.percentage ?: 0)
            }
        }
    }
}

data class StoreModel(
    @SerializedName("name")
    val name: String?
    )

data class GenresModel (
    @SerializedName("name")
    val genreName: String?
    ) {
    companion object {
        fun convertList(genres: List<GenresModel?>): List<GenresEntity> {
            return genres.map {
                GenresEntity(it?.genreName ?: "")
            }
        }
    }
}

data class PlatformModelResponse (
    @SerializedName("platform")
    val platforms: PlatformModel?
)

data class PlatformModel (
    @SerializedName("name")
    val platform: String?
    ) {
    companion object {
        fun convertList(platformModelList: List<PlatformModelResponse?>?): List<PlatformEntity> {
            return platformModelList?.map {
                PlatformEntity(it?.platforms?.platform ?: "")
            } ?: listOf(PlatformEntity(""))
        }
    }
}

data class ScreenShotModel (
    @SerializedName("image")
    val image: String?
    ) {
    companion object {
        fun convertList(images: List<ScreenShotModel?>): List<ScreenShotEntity> {
            return images.map {
                ScreenShotEntity(it?.image ?: "")
            }
        }
    }
}

