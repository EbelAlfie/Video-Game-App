package com.example.videogameapp.domain.entity

import com.google.gson.annotations.SerializedName

data class DeveloperDetailEntity (
    @SerializedName("name")
    val name: String
    )

data class PublisherDetailEntity (
    @SerializedName("name")
    val publisher: String
    )