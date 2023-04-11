package com.example.videogameapp.domain.entity


data class GameDetailedEntity (
        val id: Long,
        val desc: String,
        val name: String,
        val tbaStatus: Boolean,
        val dateReleased: String,
        val backgroundImageAdditional: String,
        val metacriticUrl: String,
        val metaCritic: Int?,
        val playtime: Int,
        val ageRating: String,
        val genres: List<GenresEntity>,
        val platforms: List<PlatformEntity>,
        val developer: List<DeveloperDetailEntity>,
        val publishers: List<PublisherDetailEntity>,
        val tags: List<String>
        )
