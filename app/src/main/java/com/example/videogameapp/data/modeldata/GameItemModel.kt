package com.example.videogameapp.data.modeldata

import com.example.videogameapp.domain.entity.GameItemEntity
import com.google.gson.annotations.SerializedName

data class GameItemResponse (
    @SerializedName("results")
    val results: List<GameItemModel>
    )

data class GameItemModel (
    @SerializedName("id")
    val id: Long?,
    @SerializedName("slug")
    val slugId: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("released")
    val dateReleased: String?,
    @SerializedName("background_image")
    val backgroundImage: String?,
    @SerializedName("metacritic")
    val metaCritic: Int?,
    @SerializedName("playtime")
    val playtime: Int?,
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
                slugId = gameItemModel.slugId ?: "",
                name = gameItemModel.name ?: "",
                dateReleased = gameItemModel.dateReleased ?: "",
                backgroundImage = gameItemModel.backgroundImage ?: "",
                metaCritic = gameItemModel.metaCritic,
                playtime = gameItemModel.playtime ?: 0,
                genres = GenresModel.convertList(gameItemModel.genres),
                platforms = PlatformModel.convertList(gameItemModel.platforms),
                screenShots = ScreenShotModel.convertList(gameItemModel.screenShots)
            )
        }
    }
}