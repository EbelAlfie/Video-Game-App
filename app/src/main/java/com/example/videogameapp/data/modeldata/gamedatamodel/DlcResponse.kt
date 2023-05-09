package com.example.videogameapp.data.modeldata.gamedatamodel

import com.google.gson.annotations.SerializedName

data class DlcResponse (
    @SerializedName("count")
    val count: Int?,
    @SerializedName("next")
    val next: String?,
    @SerializedName("results")
    val dlc: List<GameItemModel>
    )