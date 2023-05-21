package com.example.videogameapp.data.modeldata.databasemodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.videogameapp.domain.entity.gameentity.GameItemEntity
import kotlinx.coroutines.flow.*

@Entity(tableName = "game_database", indices = [Index("game_id")])
data class GameItemDbModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("db_id")
    val dbId: Long = 0,
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
    @ColumnInfo("ratings")
    val ratings: String,
    @ColumnInfo("genres")
    val genres: String,
    @ColumnInfo("platforms")
    val platforms: String,
) {
    companion object {
        fun convertList(item: List<GameItemDbModel>): List<GameItemEntity> {
            return item.map {
                        GameItemEntity(
                            id = it.gameId ?: -1,
                            name = it.name ?: "",
                            tbaStatus = it.tbaStatus ?: true,
                            dateReleased = it.dateReleased ?: "",
                            backgroundImage = it.backgroundImage ?: "",
                            metaCritic = it.metaCritic,
                            playtime = it.playtime ?: 0,
                            reviewCount = it.reviewCount ?: 0,
                            ratings = it.ratings,
                            genres = it.genres,
                            platforms = it.platforms,
                            screenShots = listOf(),
                            isInLibrary = true
                        )
            }
        }
    }
}