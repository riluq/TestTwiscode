package com.riluq.testtwiscode.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.riluq.testtwiscode.data.local.entity.FavoriteEntity

@Database(
    entities = [FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao

    companion object {
        const val DATABASE_NAME = "testtwiscode.db"
        const val FAVORITE_TABLE_NAME = "favorite"
    }
}