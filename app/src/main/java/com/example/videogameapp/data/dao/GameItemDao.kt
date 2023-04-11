package com.example.videogameapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.videogameapp.data.modeldata.databasemodel.*

@Dao
interface GameItemDao {
    //game item
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGameItem(gameItemDbModel: GameItemDbModel)

    @Query("SELECT * FROM game_database")
    fun getGameItem(): GameItemDbModel

    //sublists
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGameItemPlatform(gameItemGenre: GenresDbModel)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGameItemPlatform(gameItemPlatform: PlatformDbModel)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGameItemRating(gameItemRating: RatingDbModel)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGameItemScreenshot(gameItemScreenshot: ScreenShotDbModel)

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