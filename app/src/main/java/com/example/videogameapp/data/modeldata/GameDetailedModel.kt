package com.example.videogameapp.data.modeldata

import com.example.videogameapp.domain.entity.GameDetailedEntity
import com.google.gson.annotations.SerializedName

data class GameDetailedModel (
        @SerializedName("id")
        val id: Long?,
        @SerializedName("description")
        val desc: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("released")
        val dateReleased: String?,
        @SerializedName("background_image_additional")
        val backgroundImageAdditional: String?,
        @SerializedName("metacritic_url")
        val metacriticUrl: String?,
        @SerializedName("metacritic")
        val metaCritic: Int?,
        @SerializedName("playtime")
        val playtime: Int?,
        @SerializedName("genres")
        val genres: List<GenresModel?>,
        @SerializedName("platforms")
        val platforms: List<PlatformModel?>,//
        @SerializedName("short_screenshots")
        val screenShots: List<ScreenShotModel?>,
        @SerializedName("developers")
        val developer: List<DeveloperDetailModel?>,
        @SerializedName("publishers")
        val publishers: List<PublisherDetailModel?>
        ) {
        companion object {
                /*fun transform(it: GameDetailedModel): GameDetailedEntity {
                        return GameDetailedEntity(
                                id = it.id ?: 0,
                                desc = it.desc ?: "",
                                name = it.name ?: "",
                                dateReleased = it.dateReleased ?: "",
                                backgroundImageAdditional = it.backgroundImageAdditional ?: "",
                                metaCritic = it.metaCritic,
                                metacriticUrl = it.metacriticUrl,
                                playtime = it.playtime,
                                platforms = it.platforms,
                                screenShots = ScreenShotModel.convertList(it.screenShots),
                                developer = it.developer,
                                publishers = it.publishers,
                                genres = GenresModel.convertList(it.genres),
                        )
                }*/
        }
}