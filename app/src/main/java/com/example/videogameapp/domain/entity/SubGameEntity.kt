package com.example.videogameapp.domain.entity

import com.google.gson.annotations.SerializedName

data class RatingEntity (
    val ratingTitle: String,
    val ratingCount: Int,
    val percentage: Int
)

data class GenresEntity (
    val genreName: String
)

data class PlatformEntity (
    val platform: String
)

data class ScreenShotEntity (
    val image: String
)
