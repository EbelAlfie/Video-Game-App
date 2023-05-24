package com.example.videogameapp.domain.entity.gameentity

import android.content.Context
import android.content.res.Resources
import android.media.Rating
import androidx.core.content.res.ResourcesCompat
import com.example.videogameapp.R
import com.example.videogameapp.data.modeldata.gamedatamodel.QueryGameItemModel
import java.util.jar.Attributes

data class QueryGameItemEntity (
    var search: String? = null,
    var dates: String? = null,
    var platform: String? = null,
    var store: String? = null,
    var genres: String? = null,
    var ordering: String? = null,
    var pageSize: Int? = null,
) {
    companion object {
        fun transform(resources: Resources,queryGameItemEntity: QueryGameItemEntity): QueryGameItemModel {
            return QueryGameItemModel(
                search = queryGameItemEntity.search,
                dates = queryGameItemEntity.dates,
                platform = queryGameItemEntity.platform,
                store = queryGameItemEntity.store,
                genres = queryGameItemEntity.genres,
                ordering = convertOrdering(resources, queryGameItemEntity.ordering ?: ""),
                pageSize = queryGameItemEntity.pageSize
            )
        }

        private fun convertOrdering(resources: Resources, ordering: String): String? {
            val arrayOfOrder = resources.getStringArray(R.array.order_by)
            return when (ordering) {
                arrayOfOrder[0] -> { "relevance" }
                arrayOfOrder[1] -> { "rating" }
                arrayOfOrder[2] -> { "added"}
                arrayOfOrder[3] -> { "name" }
                arrayOfOrder[4] -> { "released" }
                arrayOfOrder[5] -> { "metacritic" }
                else -> null
            }
        }
    }
}