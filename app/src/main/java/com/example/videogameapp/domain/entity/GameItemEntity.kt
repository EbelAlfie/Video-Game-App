package com.example.videogameapp.domain.entity

import androidx.core.content.res.ResourcesCompat
import com.example.videogameapp.R
import com.example.videogameapp.data.modeldata.GenresModel
import com.example.videogameapp.data.modeldata.PlatformModelResponse
import com.example.videogameapp.data.modeldata.RatingModel
import com.example.videogameapp.data.modeldata.ScreenShotModel
import com.google.gson.annotations.SerializedName

data class GameItemEntity(
    val id: Long?,
    val slugId: String?,
    val name: String?,
    val tbaStatus: Boolean,
    val dateReleased: String?,
    val backgroundImage: String?,
    val metaCritic: Int?,
    val playtime: Int?,
    val ratings: List<RatingEntity?>,
    val reviewCount: Int,
    val genres: List<GenresEntity?>,
    val platforms: List<PlatformEntity?>,
    val screenShots: List<ScreenShotEntity?>,
){
    fun getMetacritics(): String {
        return metaCritic?.toString() ?: ""
    }

    fun getColor(): Int {
        return when (metaCritic) {
            in 0 until 40 -> R.color.red
            in 40 until 70 -> R.color.yellow
            in 70..100 -> R.color.green
            else -> R.color.red
        }
    }

    fun getRatings(): String {
        if (ratings.isEmpty()) return "-"
        return ratings.maxBy { it?.ratingCount ?: 0 }?.ratingTitle ?: "-"
    }
}
