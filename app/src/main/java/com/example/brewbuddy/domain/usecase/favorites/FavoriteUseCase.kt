package com.example.brewbuddy.domain.usecase.favorites

import javax.inject.Inject
import javax.inject.Singleton
@Singleton
data class FavoriteUseCase @Inject constructor(
    val addFavoriteUseCase: AddFavoriteUseCase,
    val removeFavoriteUseCase: RemoveFavoriteUseCase,
    val getFavoriteUseCase: GetFavoriteUseCase

)
