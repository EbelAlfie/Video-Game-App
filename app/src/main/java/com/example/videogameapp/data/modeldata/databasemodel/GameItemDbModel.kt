package com.example.videogameapp.data.modeldata.databasemodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.videogameapp.domain.entity.gameentity.GameItemEntity

@Entity(tableName = "game_database")
data class GameItemDbModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    var id: Int = 0,
    @ColumnInfo("game_id")
    val gameId: Long?,
    @ColumnInfo("name")
    val name: String?,
    @ColumnInfo("released")
    val dateReleased: String?,
    @ColumnInfo("tba")
    val tbaStatus: Boolean?,
    @ColumnInfo("background_image")
    val backgroundImage: String?,
    @ColumnInfo("metacritic")
    val metaCritic: Int?,
    @ColumnInfo("playtime")
    val playtime: Int?,
    @ColumnInfo("reviews_count")
    val reviewCount: Int?,
) {
    companion object {
        fun convertList(data: List<GameItemDbModel>): List<GameItemEntity> {
            return data.map {
                    GameItemEntity(
                        id = it.gameId ?: 0,
                        name = it.name ?: "",
                        tbaStatus = it.tbaStatus ?: true,
                        dateReleased = it.dateReleased ?: "",
                        backgroundImage = it.backgroundImage ?: "",
                        metaCritic = it.metaCritic,
                        playtime = it.playtime ?: 0,
                        reviewCount = it.reviewCount ?: 0,
                        ratings = listOf(),
                        genres = listOf(),
                        platforms = listOf(),
                        screenShots = listOf()
                    )
            }
        }
    }
}