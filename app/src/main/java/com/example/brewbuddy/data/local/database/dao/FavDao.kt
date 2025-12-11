package com.example.brewbuddy.data.local.database.dao

import androidx.room.*
import com.example.brewbuddy.data.local.database.entities.FavEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorites ORDER BY title ASC")
    fun showFavorites(): Flow<List<FavEntity>>


    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE coffeeId = :id)")
    suspend fun isFavorite(id: Int): Boolean


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(fav: FavEntity)


    @Query("DELETE FROM favorites WHERE coffeeId = :id")
    suspend fun deleteById(id: Int)
}