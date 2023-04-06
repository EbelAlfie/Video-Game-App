package com.example.videogameapp.domain.entity

import com.example.videogameapp.data.modeldata.QueryGameItemModel

data class QueryGameItemEntity (
    val search: String?,
    val dates: String?,
) {
    companion object {
        fun transform(queryGameItemEntity: QueryGameItemEntity): QueryGameItemModel {
            return QueryGameItemModel(
                search = queryGameItemEntity.search,
                dates = queryGameItemEntity.dates
            )
        }
    }
}