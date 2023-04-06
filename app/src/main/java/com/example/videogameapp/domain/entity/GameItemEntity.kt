package com.example.videogameapp.domain.entity

import com.example.videogameapp.data.modeldata.GenresModel
import com.example.videogameapp.data.modeldata.PlatformModelResponse
import com.example.videogameapp.data.modeldata.ScreenShotModel
import com.google.gson.annotations.SerializedName

data class GameItemEntity(
    val id: Long?,
    val slugId: String?,
    val name: String?,
    val dateReleased: String?,
    val backgroundImage: String?,
    val metaCritic: Int?,
    val playtime: Int?,
    val genres: List<GenresEntity?>,
    val platforms: List<PlatformEntity?>,//
    val screenShots: List<ScreenShotEntity?>,
)
