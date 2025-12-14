package com.example.brewbuddy.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.brewbuddy.data.local.database.entities.CoffeeEntity
import com.example.brewbuddy.domain.model.CoffeeCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface CoffeeDao {

    @Query("SELECT COUNT(*) FROM coffees")
    suspend fun getCoffeeCount(): Int
    
    @Query("SELECT * FROM coffees")
    fun getAllCoffees(): Flow<List<CoffeeEntity>>
    
    @Query("SELECT * FROM coffees WHERE category = :category")
    suspend fun getCoffeesByCategory(category: String): List<CoffeeEntity>
    
    @Query("SELECT * FROM coffees WHERE category = :category")
    fun getCoffeesByCategoryFlow(category: String): Flow<List<CoffeeEntity>>
    
    @Query("SELECT * FROM coffees WHERE isFavorite = 1")
    fun getFavoriteCoffees(): Flow<List<CoffeeEntity>>
    
    @Query("SELECT * FROM coffees WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun searchCoffees(query: String): Flow<List<CoffeeEntity>>
    
    @Query("UPDATE coffees SET isFavorite = :isFavorite WHERE id = :coffeeId")
    suspend fun updateFavoriteStatus(coffeeId: Int, isFavorite: Boolean)
    
    @Query("DELETE FROM coffees")
    suspend fun clearAllCoffees()
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoffees(coffees: List<CoffeeEntity>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoffee(coffee: CoffeeEntity)
}