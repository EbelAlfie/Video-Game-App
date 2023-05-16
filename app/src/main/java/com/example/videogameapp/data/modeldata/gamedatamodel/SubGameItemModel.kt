package com.example.videogameapp.data.modeldata.gamedatamodel

import com.example.videogameapp.domain.entity.*
import com.example.videogameapp.domain.entity.gameentity.*
import com.google.gson.annotations.SerializedName

data class RatingModel (
    @SerializedName("title")
    val ratingTitle: String?,
    @SerializedName("count")
    val ratingCount: Int?,
    @SerializedName("percent")
    val percentage: Float?
    ) {
    companion object {
        fun convertList(ratingModelList: List<RatingModel?>) : List<RatingEntity> {
            return ratingModelList.map {
                RatingEntity(it?.ratingTitle ?: "", it?.ratingCount ?: 0, it?.percentage ?: 0f)
            }
        }

        fun ratingString(ratingModelList: List<RatingModel>) : String {
            return if (ratingModelList.isEmpty()) "" else ratingModelList.maxBy { it.ratingCount ?: 0 }.ratingTitle ?: ""
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

        fun genreString(genres: List<GenresModel>?): String {
            return genres?.map {
                it.genreName ?: ""
            }?.joinToString { it } ?: ""
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

        fun platformString(platformModelList: List<PlatformModelResponse?>?): String {
            return platformModelList?.map {
                it?.platforms?.platform ?: ""
            }?.joinToString { it } ?: ""
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

        fun screenshootString(images: List<ScreenShotModel>?): List<String> {
            return images?.map {
                it.image ?: ""
            } ?: listOf()
        }
    }
}

