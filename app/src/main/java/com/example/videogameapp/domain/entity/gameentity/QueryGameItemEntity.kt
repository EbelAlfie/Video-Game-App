package com.example.videogameapp.domain.entity.gameentity

import com.example.videogameapp.data.modeldata.gamedatamodel.QueryGameItemModel

data class QueryGameItemEntity (
    var search: String? = null,
    var dates: String? = null,
    var platform: String? = null,
    var store: String? = null,
    var genres: String? = null,
    var ordering: String? = null,
    var page: Int? = null
) {
    companion object {
        fun transform(queryGameItemEntity: QueryGameItemEntity): QueryGameItemModel {
            return QueryGameItemModel(
                search = queryGameItemEntity.search,
                dates = queryGameItemEntity.dates,
                platform = queryGameItemEntity.platform,
                store = queryGameItemEntity.store,
                genres = queryGameItemEntity.genres,
                ordering = convertOrdering(queryGameItemEntity.ordering ?: ""),
                page = queryGameItemEntity.page
            )
        }

        fun convertOrdering(ordering: String): String {
            return ordering
        }
    }
}