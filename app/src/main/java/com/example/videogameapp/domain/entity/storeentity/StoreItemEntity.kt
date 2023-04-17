package com.example.videogameapp.domain.entity.storeentity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoreItemEntity (
    val storeId: Long,
    val name: String,
    val gamesCount: Int,
    val image: String,
    val games: List<GameEntity>
    ): Parcelable

@Parcelize
data class GameEntity (
    val added: Long,
    val gameName: String
): Parcelable