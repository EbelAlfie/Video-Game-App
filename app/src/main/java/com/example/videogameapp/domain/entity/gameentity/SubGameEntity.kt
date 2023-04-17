package com.example.videogameapp.domain.entity.gameentity

import android.os.Parcelable
import com.example.videogameapp.data.modeldata.databasemodel.GenresDbModel
import com.example.videogameapp.data.modeldata.databasemodel.PlatformDbModel
import com.example.videogameapp.data.modeldata.databasemodel.RatingDbModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class RatingEntity (
    val ratingTitle: String,
    val ratingCount: Int,
    val percentage: Int
): Parcelable{
    companion object {
        fun toRatingModel(id: Long, ratingList: List<RatingEntity?>): List<RatingDbModel> {
            return ratingList.map {
                RatingDbModel(id, it?.ratingTitle ?: "", it?.ratingCount ?: 0, it?.percentage ?: 0)
            }
        }
    }
}

@Parcelize
data class GenresEntity (
    val genreName: String
): Parcelable{
    companion object {
        fun toGenreModel(id: Long, genreList: List<GenresEntity?>): List<GenresDbModel> {
            return genreList.map {
                GenresDbModel(id, it?.genreName ?: "")
            }
        }
    }
}

@Parcelize
data class PlatformEntity (
    val platform: String
): Parcelable {
    companion object {
        fun toPlatformModel(id: Long, platformList: List<PlatformEntity?>): List<PlatformDbModel> {
            return platformList.map {
                PlatformDbModel(id, it?.platform ?: "")
            }
        }
    }
}

@Parcelize
data class ScreenShotEntity (
    val image: String
): Parcelable
