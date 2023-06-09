package com.example.videogameapp.data.modeldata.gamedatamodel

data class QueryGameItemModel (
    val search: String?,
    val dates: String?,
    val platform: String?,
    val store: String?,
    val genres: String?,
    val ordering: String?,
    val pageSize: Int?,
    )