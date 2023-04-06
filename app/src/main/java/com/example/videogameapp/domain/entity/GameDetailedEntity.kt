package com.example.videogameapp.domain.entity

import com.example.videogameapp.data.modeldata.*
import com.google.gson.annotations.SerializedName

data class GameDetailedEntity (
        val id: Long,
        val desc: String,
        val name: String,
        val dateReleased: String,
        val backgroundImageAdditional: String,
        val metacriticUrl: String,
        val metaCritic: Int?,
        val playtime: Int,
        val genres: List<GenresEntity>,
        val platforms: List<PlatformEntity>,
        val screenShots: List<ScreenShotEntity>,
        val developer: List<DeveloperDetailEntity>,
        val publishers: List<PublisherDetailEntity>
        )