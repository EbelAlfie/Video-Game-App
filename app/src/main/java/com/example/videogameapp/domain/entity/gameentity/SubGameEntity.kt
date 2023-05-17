package com.example.videogameapp.domain.entity.gameentity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RatingEntity (
    val ratingTitle: String,
    val ratingCount: Int,
    val percentage: Float
): Parcelable{
    companion object {
        fun ratingString(ratingModelList: List<RatingEntity>) : String {
            return if (ratingModelList.isEmpty()) "" else ratingModelList.maxBy { it.ratingCount ?: 0 }.ratingTitle ?: ""
        }
    }
}

@Parcelize
data class GenresEntity (
    val genreName: String
): Parcelable{
    companion object {

        fun genreString(genres: List<GenresEntity>?): String {
            return genres?.map {
                it.genreName
            }?.joinToString { it } ?: ""
        }
    }
}

@Parcelize
data class PlatformEntity (
    val platform: String
): Parcelable {
    companion object {
        fun platformString(platformModelList: List<PlatformEntity?>?): String {
            return platformModelList?.map {
                it?.platform ?: ""
            }?.joinToString { it } ?: ""
        }
    }
}

@Parcelize
data class ScreenShotEntity (
    val image: String
): Parcelable
