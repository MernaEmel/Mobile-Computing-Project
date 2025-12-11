package com.example.brewbuddy.data.repository.impl

import com.example.brewbuddy.data.local.database.dao.FavoritesDao
import com.example.brewbuddy.data.local.database.entities.FavEntity
import com.example.brewbuddy.domain.model.Favorite
import com.example.brewbuddy.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepositoryImpl @Inject constructor(
    private val dao: FavoritesDao
) : FavoritesRepository {
    override fun getFavorites(): Flow<List<Favorite>> = dao.showFavorites().map { list ->
        list.map { Favorite(it.coffeeId, it.title, it.image, it.priceCents, it.isHot) }
    }

    override suspend fun addFavorite(fav: Favorite) {
        dao.upsert(FavEntity(fav.id, fav.name, fav.image, fav.priceCents, fav.isHot))
    }

    override suspend fun removeFavorite(id: Int) {
        dao.deleteById(id)
    }

    override suspend fun isFavorite(id: Int): Boolean = dao.isFavorite(id)
}