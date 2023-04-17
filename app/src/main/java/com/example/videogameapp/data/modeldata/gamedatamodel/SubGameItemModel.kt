package com.example.videogameapp.data.modeldata.gamedatamodel

import com.example.videogameapp.domain.entity.*
import com.example.videogameapp.domain.entity.gameentity.*
import com.google.gson.annotations.SerializedName

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

data class StoreDetailResponseModel(
    @SerializedName("store")
    val store: StoreModel
) {
    companion object {
        fun convertList(list: List<StoreDetailResponseModel?>): List<StoreEntity> {
            return list.map {
                StoreEntity(it?.store?.id ?: 0, it?.store?.name ?: "", it?.store?.url ?: "")
            }
        }
    }
}

data class StoreModel(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?,
    ) {
    companion object {
        /*fun convertList(stores: List<StoreDetailResponseModel?>): List<StoreEntity> {
            return stores.map {
                StoreEntity(it?.store?.id ?: 0, it?.store?.name ?: "")
            }
        }*/
        fun convertList(stores: List<StoreModel?>): List<StoreEntity> {
            return stores.map {
                StoreEntity(it?.id ?: 0, it?.name ?: "", it?.url ?: "")
            }
        }
    }
}

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

