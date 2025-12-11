package com.example.brewbuddy.domain.repository


import com.example.brewbuddy.domain.model.Favorite
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun getFavorites(): Flow<List<Favorite>>
    suspend fun addFavorite(fav: Favorite)
    suspend fun removeFavorite(id: Int)
    suspend fun isFavorite(id: Int): Boolean
}
