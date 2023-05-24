package com.example.videogameapp.domain.entity.gameentity

import com.example.videogameapp.R
import java.text.SimpleDateFormat
import java.util.*


data class GameDetailedEntity (
    val id: Long,
    val desc: String,
    var name: String,
    var tbaStatus: Boolean,
    var dateReleased: String,
    val metacriticUrl: String,
    var metaCritic: Int?,
    var playtime: Int,
    var poster: String?,
    val ageRating: String,
    val ratings: List<RatingEntity>,
    var genres: List<GenresEntity>,
    var platforms: List<PlatformEntity>,
    val developer: List<DeveloperDetailEntity>,
    val publishers: List<PublisherDetailEntity>,
    val tags: List<TagsEntity>,
    var screenShots: List<ScreenShotEntity>,
    val store: List<StoreEntity>,
    var isInLibrary: Boolean
    ){
    companion object {
        fun getNullableString(string: String): String {
            return string.ifBlank { "-" }
        }
    }

    fun getMetacritics(): String {
        return metaCritic?.toString() ?: ""
    }

    fun getMetacriticColor(): Int {
        return when (metaCritic) {
            in 0 until 40 -> R.color.red
            in 40 until 70 -> R.color.yellow
            in 70..100 -> R.color.green
            else -> R.color.red
        }
    }

    fun getDetailReleasedDate(): String {
        if (tbaStatus) return "TBA"
        return try {
            val formater = SimpleDateFormat("yyyy-mm-dd", Locale.CANADA).parse(dateReleased)
            if (formater != null) SimpleDateFormat("dd MMM yyyy", Locale.CANADA).format(formater) else "-"
        }catch (e: Exception) { "-" }
    }
}
