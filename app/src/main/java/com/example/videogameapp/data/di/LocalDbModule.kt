package com.example.videogameapp.data.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.videogameapp.data.dao.GameItemDao
import com.example.videogameapp.data.modeldata.databasemodel.*
import dagger.Module
import dagger.Provides


@Module
@Database(entities = [GameItemDbModel::class], version = 1)
abstract class LocalDbModule: RoomDatabase() {
    abstract fun gameItemDao() : GameItemDao
    companion object {
        @Volatile
        private var Object: LocalDbModule? = null
        @Provides
        @JvmStatic
        fun getDatabase(context: Context): LocalDbModule {
            if (Object == null) {
                synchronized(LocalDbModule::class.java) {
                    Object = Room.databaseBuilder(context.applicationContext,
                        LocalDbModule::class.java, "app_db")
                        .build()
                }
            }
            return Object as LocalDbModule
        }
    }
}