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
        @SerializedName("tba")
        val tbaStatus: Boolean,
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
        val platforms: List<PlatformModelResponse?>,
        @SerializedName("developers")
        val developer: List<DeveloperDetailModel?>,
        @SerializedName("publishers")
        val publishers: List<PublisherDetailModel?>,
        @SerializedName("esrb_rating")
        val esrbRating: AgeRatingModel
        ) {
        companion object {
                fun convert(it: GameDetailedModel): GameDetailedEntity {
                        return GameDetailedEntity(
                                id = it.id ?: 0,
                                desc = it.desc ?: "",
                                name = it.name ?: "",
                                dateReleased = it.dateReleased ?: "",
                                backgroundImageAdditional = it.backgroundImageAdditional ?: "",
                                tbaStatus = it.tbaStatus,
                                metaCritic = it.metaCritic,
                                metacriticUrl = it.metacriticUrl ?: "",
                                playtime = it.playtime ?: 0,
                                ageRating = it.esrbRating.ageRating,
                                platforms = PlatformModel.convertList(it.platforms),
                                developer = DeveloperDetailModel.convertList(it.developer),
                                publishers = PublisherDetailModel.convertList(it.publishers),
                                genres = GenresModel.convertList(it.genres)
                        )
                }
        }
}