package com.example.videogameapp.domain.entity.gameentity

import android.content.Context
import android.os.Parcelable
import com.example.videogameapp.R
import com.example.videogameapp.data.modeldata.databasemodel.GameItemDbModel
import kotlinx.parcelize.Parcelize

data class GameItemEntity(
    val id: Long,
    val name: String,
    val tbaStatus: Boolean,
    val dateReleased: String,
    val backgroundImage: String,
    val metaCritic: Int?,
    val playtime: Int,
    val reviewCount: Int,
    val ratings: String,
    val genres: String,
    val platforms: String,
    val screenShots: List<String>,
    var isInLibrary: Boolean
){
    companion object {
        fun transformDbModel(gameItemEntity: GameItemEntity): GameItemDbModel {
            return GameItemDbModel(
                gameId = gameItemEntity.id,
                name = gameItemEntity.name,
                tbaStatus = gameItemEntity.tbaStatus,
                dateReleased = gameItemEntity.dateReleased,
                backgroundImage = gameItemEntity.backgroundImage,
                metaCritic = gameItemEntity.metaCritic,
                playtime = gameItemEntity.playtime,
                reviewCount = gameItemEntity.reviewCount,
                ratings = gameItemEntity.ratings,
                genres = gameItemEntity.genres,
                platforms = gameItemEntity.platforms
            )
        }
    }
    fun getMetacritics(): String {
        return metaCritic?.toString() ?: ""
    }

    fun getMetacriticColor(): Int {
        return when (metaCritic) {
            in 0 until 40 -> R.color.red
            in 40 until 70 -> R.color.yellow
            in 70..100 -> R.color.green
            else -> R.color.red
        }
    }

    fun getReviewColor(context: Context) : Int {
        return when (ratings) {
            context.getString(R.string.exceptional_rating) -> R.color.green
            context.getString(R.string.recommended_rating) -> R.color.blue
            context.getString(R.string.meh_rating) -> R.color.yellow
            else -> R.color.red
        }
    }

    fun getReleasedDate(): String {
        return if (!tbaStatus) dateReleased else "TBA"
    }
}
