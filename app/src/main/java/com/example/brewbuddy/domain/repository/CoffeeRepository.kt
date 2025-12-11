package com.example.brewbuddy.domain.repository

import com.example.brewbuddy.data.local.database.entities.CoffeeEntity
import com.example.brewbuddy.domain.model.Coffee
import com.example.brewbuddy.domain.model.CoffeeCategory
import kotlinx.coroutines.flow.Flow

interface CoffeeRepository {
    suspend fun getCoffeesByCategory(category: String): List<CoffeeEntity>
    suspend fun fetchHotCoffees(): List<CoffeeEntity>
    suspend fun fetchColdCoffees(): List<CoffeeEntity>
    fun getCachedCoffees(): Flow<List<CoffeeEntity>>
    fun getCoffeesByCategory(category: CoffeeCategory): Flow<List<Coffee>>
    fun getFavoriteCoffees(): Flow<List<Coffee>>
    fun searchCoffees(query: String): Flow<List<Coffee>>
    suspend fun refreshCoffees()
    suspend fun toggleFavorite(coffeeId: Int, isFavorite: Boolean)
    suspend fun getBestSellerCoffee(): Coffee
    suspend fun getWeekRecommendation(): List<Coffee>
}
