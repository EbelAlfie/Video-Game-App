package com.example.videogameapp.domain.entity.gameentity

import com.example.videogameapp.data.modeldata.gamedatamodel.QueryGameItemModel

data class QueryGameItemEntity (
    val search: String?,
    val dates: String?,
    val platform: String?,
    val store: String?,
    val ordering: String?
) {
    companion object {
        fun transform(queryGameItemEntity: QueryGameItemEntity): QueryGameItemModel {
            return QueryGameItemModel(
                search = queryGameItemEntity.search,
                dates = queryGameItemEntity.dates,
                platform = queryGameItemEntity.platform,
                store = queryGameItemEntity.store,
                ordering = queryGameItemEntity.ordering
            )
        }
    }
}