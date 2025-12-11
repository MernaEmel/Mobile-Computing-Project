package com.example.brewbuddy.domain.usecase.favorites

import com.example.brewbuddy.domain.model.Favorite
import com.example.brewbuddy.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
    operator fun invoke(): Flow<List<Favorite>> = repository.getFavorites()
}
