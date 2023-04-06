package com.example.videogameapp.data.modeldata

import com.google.gson.annotations.SerializedName

data class DeveloperDetailModel (
    @SerializedName("name")
    val name: String
    )

data class PublisherDetailModel (
    @SerializedName("name")
    val publisher: String
    )