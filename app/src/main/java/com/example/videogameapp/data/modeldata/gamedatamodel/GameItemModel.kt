package com.example.videogameapp.data.modeldata.gamedatamodel

import com.example.videogameapp.domain.entity.gameentity.GameItemEntity
import com.google.gson.annotations.SerializedName

data class GameItemResponse (
    @SerializedName("results")
    val results: List<GameItemModel>
    )

data class GameItemModel (
    @SerializedName("id")
    val id: Long?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("released")
    val dateReleased: String?,
    @SerializedName("tba")
    val tbaStatus: Boolean?,
    @SerializedName("background_image")
    val backgroundImage: String?,
    @SerializedName("metacritic")
    val metaCritic: Int?,
    @SerializedName("playtime")
    val playtime: Int?,
    @SerializedName("ratings")
    val ratings: List<RatingModel?>,
    @SerializedName("reviews_count")
    val reviewCount: Int?,
    @SerializedName("genres")
    val genres: List<GenresModel?>,
    @SerializedName("platforms")
    val platforms: List<PlatformModelResponse?>,
    @SerializedName("short_screenshots")
    val screenShots: List<ScreenShotModel?>,
) {
    companion object {
        fun convertList(listGameItemModel: List<GameItemModel?>): List<GameItemEntity> {
            return listGameItemModel.map {
                convertItem(it ?: return emptyList())
            }
        }

        private fun convertItem(gameItemModel: GameItemModel): GameItemEntity {
            return GameItemEntity(
                id = gameItemModel.id ?: 0,
                name = gameItemModel.name ?: "",
                tbaStatus = gameItemModel.tbaStatus ?: true,
                dateReleased = gameItemModel.dateReleased ?: "",
                backgroundImage = gameItemModel.backgroundImage ?: "",
                metaCritic = gameItemModel.metaCritic,
                playtime = gameItemModel.playtime ?: 0,
                ratings = RatingModel.convertList(gameItemModel.ratings),
                reviewCount = gameItemModel.reviewCount ?: 0,
                genres = GenresModel.convertList(gameItemModel.genres),
                platforms = PlatformModel.convertList(gameItemModel.platforms),
                screenShots = ScreenShotModel.convertList(gameItemModel.screenShots)
            )
        }
    }
}