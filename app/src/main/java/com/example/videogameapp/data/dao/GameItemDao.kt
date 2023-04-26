package com.example.videogameapp.data.dao

import androidx.room.*
import com.example.videogameapp.data.modeldata.databasemodel.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GameItemDao {
    //game item
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGameItem(gameItemDbModel: GameItemDbModel): Long

    @Query("SELECT * FROM game_database")
    suspend fun getAllGameLibrary(): List<GameItemDbModel>

    //sublists
    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGameItemGenre(gameItemGenre: GenresDbModel)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGameItemPlatform(gameItemPlatform: PlatformDbModel)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGameItemRating(gameItemRating: RatingDbModel)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGameItemScreenshot(gameItemScreenshot: List<ScreenShotDbModel>)

    //query sublists
    @Query("SELECT * FROM rating_db WHERE id=:ratingId")
    suspend fun getSpecificRating(ratingId: Int): List<RatingDbModel>
    @Query("SELECT * FROM genre_db WHERE id=:genreId")
    suspend fun getSpecificGenre(genreId: Int): List<GenresDbModel>
    @Query("SELECT * FROM platform_db WHERE id=:platformId")
    suspend fun getSpecificPlatform(platformId: Int): List<PlatformDbModel>
    @Query("SELECT * FROM screenshot_db WHERE id=:screenShotId")
    fun getSpecificScreenshot(screenShotId: Int): List<ScreenShotDbModel>*/

}