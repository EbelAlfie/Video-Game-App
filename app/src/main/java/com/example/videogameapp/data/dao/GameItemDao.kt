package com.example.videogameapp.data.dao

import androidx.room.*
import com.example.videogameapp.data.modeldata.databasemodel.*

@Dao
interface GameItemDao {
    //game item
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGameItem(gameItemDbModel: GameItemDbModel): Long

    @Query("SELECT * FROM game_database")
    suspend fun getAllGameLibrary(): List<GameItemDbModel>

    @Query("SELECT * FROM game_database WHERE game_id=:id")
    suspend fun getSpecificGame(id: Long): GameItemDbModel?

    @Query("DELETE FROM game_database WHERE game_id=:id")
    suspend fun deleteGameItem(id: Long): Int

}