package com.riluq.testtwiscode.data.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.riluq.testtwiscode.data.local.entity.FavoriteEntity

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteEntity: FavoriteEntity): Long

    @Delete
    suspend fun delete(favoriteEntity: FavoriteEntity)

    @Update
    suspend fun update(favoriteEntity: FavoriteEntity)

    @Query("SELECT * from favorite ORDER by id DESC")
    fun getAllFavorite(): DataSource.Factory<Int, FavoriteEntity>

    @Query("SELECT * FROM favorite WHERE doctorId = :id")
    fun getFavoriteByDoctorId(id: Int): LiveData<FavoriteEntity>

    @Query("SELECT * FROM favorite WHERE doctorName LIKE :query OR specialist LIKE :query")
    fun search(query: String): DataSource.Factory<Int, FavoriteEntity>
}