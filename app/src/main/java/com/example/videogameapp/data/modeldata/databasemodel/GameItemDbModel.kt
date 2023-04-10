package com.example.videogameapp.data.modeldata.databasemodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.videogameapp.data.modeldata.GenresModel
import com.example.videogameapp.data.modeldata.PlatformModelResponse
import com.example.videogameapp.data.modeldata.RatingModel
import com.example.videogameapp.data.modeldata.ScreenShotModel
import com.google.gson.annotations.SerializedName

@Entity(tableName = "game_database")
data class GameItemDbModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int? = 0,
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
    @ColumnInfo("rating_id")
    val ratingsId: Int,
    @ColumnInfo("genres_id")
    val genresId: Int,
    @ColumnInfo("platforms_id")
    val platformsId: Int,
    @ColumnInfo("short_screenshots_id")
    val screenshotId: Int,
)
