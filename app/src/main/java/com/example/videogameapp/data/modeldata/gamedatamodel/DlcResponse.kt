package com.example.videogameapp.data.modeldata.gamedatamodel

import com.google.gson.annotations.SerializedName

data class DlcResponse (
    @SerializedName("results")
    val dlc: List<GameItemModel>
    )