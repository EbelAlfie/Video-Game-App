package com.example.videogameapp.domain.entity.gameentity

import android.os.Parcelable
import com.example.videogameapp.data.modeldata.databasemodel.GenresDbModel
import com.example.videogameapp.data.modeldata.databasemodel.PlatformDbModel
import com.example.videogameapp.data.modeldata.databasemodel.RatingDbModel
import com.example.videogameapp.data.modeldata.gamedatamodel.GenresModel
import com.example.videogameapp.data.modeldata.gamedatamodel.PlatformModelResponse
import com.example.videogameapp.data.modeldata.gamedatamodel.RatingModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class RatingEntity (
    val ratingTitle: String,
    val ratingCount: Int,
    val percentage: Float
): Parcelable{
    companion object {
        fun toRatingModel(id: Long, ratingList: List<RatingEntity?>): List<RatingDbModel> {
            return ratingList.map {
                RatingDbModel(id, it?.ratingTitle ?: "", it?.ratingCount ?: 0, it?.percentage ?: 0f)
            }
        }
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
        fun toGenreModel(id: Long, genreList: List<GenresEntity?>): List<GenresDbModel> {
            return genreList.map {
                GenresDbModel(id, it?.genreName ?: "")
            }
        }

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
        fun toPlatformModel(id: Long, platformList: List<PlatformEntity?>): List<PlatformDbModel> {
            return platformList.map {
                PlatformDbModel(id, it?.platform ?: "")
            }
        }
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
