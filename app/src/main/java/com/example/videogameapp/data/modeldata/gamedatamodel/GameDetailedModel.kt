package com.example.videogameapp.data.modeldata.gamedatamodel

import android.media.Rating
import com.example.videogameapp.domain.entity.gameentity.*
import com.google.gson.annotations.SerializedName

data class GameDetailedModel (
    @SerializedName("id")
        val id: Long?,
    @SerializedName("description")
        val desc: String?,
    @SerializedName("name")
        val name: String?,
    @SerializedName("metacritic_url")
        val metacriticUrl: String?,
    @SerializedName("metacritic")
        val metacritic: Int?,
    @SerializedName("tba")
        val tbaStatus: Boolean?,
    @SerializedName("background_image")
        val poster : String? ,
    @SerializedName("playtime")
        val playtime: Int?,
    @SerializedName("released")
        val dateReleased: String?,
    @SerializedName("developers")
        val developer: List<DeveloperDetailModel?>,
    @SerializedName("ratings")
        val ratings: List<RatingModel>,
    @SerializedName("publishers")
        val publishers: List<PublisherDetailModel?>,
    @SerializedName("esrb_rating")
        val esrbRating: AgeRatingModel?,
    @SerializedName("tags")
        val tags: List<TagsModel?>,
    @SerializedName("stores")
        val stores: List<StoreDetailResponseModel?>,
    @SerializedName("genres")
        var genres: List<GenresModel>,
    @SerializedName("platforms")
        var platforms: List<PlatformModelResponse>,
        var isInLibrary: Boolean = false
        ) {
        companion object {
                fun convert(it: GameDetailedModel): GameDetailedEntity {
                    return GameDetailedEntity(
                        id = it.id ?: 0,
                        desc = it.desc ?: "",
                        name = it.name ?: "",
                        dateReleased = it.dateReleased ?: "",
                        tbaStatus = it.tbaStatus ?: false,
                        metaCritic = it.metacritic,
                        metacriticUrl = it.metacriticUrl ?: "",
                        playtime = it.playtime ?: 0,
                        ageRating = it.esrbRating?.ageRating ?: "",
                        platforms = PlatformModel.convertList(it.platforms),
                        developer = DeveloperDetailModel.convertList(it.developer),
                        publishers = PublisherDetailModel.convertList(it.publishers),
                        genres = GenresModel.convertList(it.genres),
                        ratings = RatingModel.convertList(it.ratings),
                        tags = TagsModel.convertList(it.tags),
                        store = StoreDetailResponseModel.convertList(it.stores),
                        screenShots = listOf(),
                        poster = it.poster,
                        isInLibrary = it.isInLibrary
                    )
                }

            fun convertToGameItem(gameDetail: GameDetailedModel?): GameItemEntity {
                return GameItemEntity(
                    id = gameDetail?.id ?: 0,
                    name = gameDetail?.name ?: "",
                    tbaStatus = gameDetail?.tbaStatus ?: true,
                    dateReleased = gameDetail?.dateReleased ?: "",
                    backgroundImage = gameDetail?.poster ?: "",
                    metaCritic = gameDetail?.metacritic,
                    playtime = gameDetail?.playtime ?: 0,
                    reviewCount = 1,
                    genres = GenresModel.genreString(gameDetail?.genres),
                    platforms = PlatformModel.platformString(gameDetail?.platforms),
                    screenShots = listOf(),
                    ratings = RatingModel.ratingString(gameDetail?.ratings ?: listOf()),
                    isInLibrary = gameDetail?.isInLibrary ?: false
                )
            }
        }
}