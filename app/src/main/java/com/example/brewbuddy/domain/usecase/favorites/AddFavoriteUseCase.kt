package com.example.brewbuddy.domain.usecase.favorites

import com.example.brewbuddy.domain.model.Favorite
import com.example.brewbuddy.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
    suspend operator fun invoke(fav: Favorite) = repository.addFavorite(fav)
}