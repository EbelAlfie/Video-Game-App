package com.example.videogameapp.domain.entity.gameentity

import com.example.videogameapp.R


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
    var genres: List<GenresEntity>,
    var platforms: List<PlatformEntity>,
    val developer: List<DeveloperDetailEntity>,
    val publishers: List<PublisherDetailEntity>,
    val tags: List<TagsEntity>,
    var screenShots: List<ScreenShotEntity>,
    val store: List<StoreEntity>
    ){

    //setters
    fun setGameName(gameName: String) { name = gameName }
    fun setGameDateReleased(gameDateReleased: String) { dateReleased = gameDateReleased }
    fun setGameTba(gameTbaStatus: Boolean) { tbaStatus = gameTbaStatus }
    fun setGameMetaCritic(gameMetaCritic: Int?) { metaCritic = gameMetaCritic }
    fun setGamePlaytime(gamePlaytime: Int) { playtime = gamePlaytime }
    fun setGamePlatforms(gamePlatforms: List<PlatformEntity>) { platforms = gamePlatforms}
    fun setGameGenres(gameGenres: List<GenresEntity>) { genres = gameGenres}
    fun setGamePoster(gamePoster: String?) { poster = gamePoster}
    fun setGameScreenshots(gameScreenshot: List<ScreenShotEntity>) { screenShots = gameScreenshot}

    fun getMetacritics(): String {
        return metaCritic?.toString() ?: ""
    }

    fun getReleasedDate(): String {
        return if (!tbaStatus) dateReleased else "TBA"
    }

    fun getMetacriticColor(): Int {
        return when (metaCritic) {
            in 0 until 40 -> R.color.red
            in 40 until 70 -> R.color.yellow
            in 70..100 -> R.color.green
            else -> R.color.red
        }
    }
}
