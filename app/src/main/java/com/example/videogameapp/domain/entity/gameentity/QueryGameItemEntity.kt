package com.example.videogameapp.domain.entity.gameentity

import com.example.videogameapp.data.modeldata.gamedatamodel.QueryGameItemModel

data class QueryGameItemEntity (
    var search: String?,
    var dates: String?,
    var platform: String?,
    var store: String?,
    var ordering: String?,
    var page: Int?
) {
    companion object {
        fun transform(queryGameItemEntity: QueryGameItemEntity): QueryGameItemModel {
            return QueryGameItemModel(
                search = queryGameItemEntity.search,
                dates = queryGameItemEntity.dates,
                platform = queryGameItemEntity.platform,
                store = queryGameItemEntity.store,
                ordering = convertOrdering(queryGameItemEntity.ordering ?: ""),
                page = queryGameItemEntity.page
            )
        }

        fun convertOrdering(ordering: String): String {
            return ordering
        }
    }
}