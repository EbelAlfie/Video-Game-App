package com.example.videogameapp.data.modeldata.storedatamodel

import com.example.videogameapp.domain.entity.storeentity.GameEntity
import com.example.videogameapp.domain.entity.storeentity.StoreItemEntity
import com.google.gson.annotations.SerializedName

data class StoreItemResponseModel (
        @SerializedName("results")
        val stores: List<StoreItemModel?>
        )

data class StoreItemModel(
        @SerializedName("id")
        val storeId: Long?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("games_count")
        val gamesCount: Int?,
        @SerializedName("image_background")
        val image: String?,
        @SerializedName("games")
        val games: List<GameModel?>
) {
        companion object {
                fun convertList(storeList: List<StoreItemModel?>): List<StoreItemEntity> {
                        return storeList.map {
                                StoreItemEntity(
                                        storeId = it?.storeId ?: 0L,
                                        name = it?.name ?: "",
                                        gamesCount = it?.gamesCount ?: 0,
                                        image = it?.image ?: "",
                                        games = GameModel.convertList(it?.games ?: listOf())
                                )
                        }
                }
        }
}

data class GameModel (
        @SerializedName("added")
        val added: Long?,
        @SerializedName("name")
        val gameName: String?
        ) {
        companion object {
                fun convertList(gameList : List<GameModel?>): List<GameEntity> {
                        return gameList.map {
                                GameEntity(it?.added ?: 0, it?.gameName ?: "")
                        }
                }
        }
}