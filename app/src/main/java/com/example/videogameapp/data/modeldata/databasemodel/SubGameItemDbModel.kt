package com.example.videogameapp.data.modeldata.databasemodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rating_db")
data class RatingDbModel (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("id")
    val id: Long,
    @ColumnInfo("title")
    val ratingTitle: String,
    @ColumnInfo("count")
    val ratingCount: Int,
    @ColumnInfo("percentage")
    val percentage: Float
)

@Entity(tableName = "genre_db")
data class GenresDbModel (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("id")
    val id: Long,
    @ColumnInfo("name")
    val genreName: String
)

@Entity(tableName = "platform_db")
data class PlatformDbModel (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("id")
    val id: Long,
    @ColumnInfo("name")
    val platform: String
)

@Entity(tableName = "screenshot_db")
data class ScreenShotDbModel (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("id")
    val id: Long,
    @ColumnInfo("image")
    val image: String
)