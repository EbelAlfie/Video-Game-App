package com.example.videogameapp.data.dao

import androidx.room.*
import com.example.videogameapp.data.modeldata.databasemodel.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GameItemDao {
    //game item
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGameItem(gameItemDbModel: GameItemDbModel)

    @Query("SELECT * FROM game_database")
    suspend fun getAllGameLibrary(): Flow<List<GameItemDbModel>>

    //sublists
    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGameItemGenre(gameItemGenre: List<GenresDbModel>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGameItemPlatform(gameItemPlatform: List<PlatformDbModel>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGameItemRating(gameItemRating: List<RatingDbModel>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGameItemScreenshot(gameItemScreenshot: List<ScreenShotDbModel>)*/

    //query sublists
//    @Query("SELECT * FROM rating_db WHERE id=:ratingId")
//    fun getSpecificRating(ratingId: Int): List<RatingDbModel>
//    @Query("SELECT * FROM rating_db WHERE id=:genreId")
//    fun getSpecificGenre(genreId: Int): List<GenresDbModel>
//    @Query("SELECT * FROM rating_db WHERE id=:platformId")
//    fun getSpecificPlatform(platformId: Int): List<PlatformDbModel>
//    @Query("SELECT * FROM rating_db WHERE id=:screenShotId")
//    fun getSpecificScreenshot(screenShotId: Int): List<ScreenShotDbModel>

}