package com.example.videogameapp.data.modeldata.gamedatamodel

import com.example.videogameapp.domain.entity.gameentity.GameDetailedEntity
import com.google.gson.annotations.SerializedName

data class GameDetailedModel (
    @SerializedName("id")
    val id: Long?,
    @SerializedName("description")
        val desc: String?,
    @SerializedName("metacritic_url")
        val metacriticUrl: String?,
    @SerializedName("developers")
        val developer: List<DeveloperDetailModel?>,
    @SerializedName("publishers")
        val publishers: List<PublisherDetailModel?>,
    @SerializedName("esrb_rating")
        val esrbRating: AgeRatingModel?,
    @SerializedName("tags")
        val tags: List<TagsModel?>,
    @SerializedName("stores")
        val stores: List<StoreDetailResponseModel?>
        ) {
        companion object {
                fun convert(it: GameDetailedModel): GameDetailedEntity {
                    return GameDetailedEntity(
                        id = it.id ?: 0,
                        desc = it.desc ?: "",
                        name = "",
                        dateReleased = "",
                        tbaStatus = false,
                        metaCritic = null,
                        metacriticUrl = it.metacriticUrl ?: "",
                        playtime = 0,
                        ageRating = it.esrbRating?.ageRating ?: "",
                        platforms = listOf(),
                        developer = DeveloperDetailModel.convertList(it.developer),
                        publishers = PublisherDetailModel.convertList(it.publishers),
                        genres = listOf(),
                        tags = TagsModel.convertList(it.tags),
                        store = StoreDetailResponseModel.convertList(it.stores),
                        screenShots = listOf(),
                        poster = ""
                    )
                }
        }
}