package com.example.brewbuddy

import com.example.brewbuddy.domain.model.Favorite
import com.example.brewbuddy.domain.repository.FavoritesRepository
import com.example.brewbuddy.domain.usecase.favorites.AddFavoriteUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class AddFavoriteUseCaseTest {

    private val repo = mock<FavoritesRepository>()
    private val useCase = AddFavoriteUseCase(repo)

    @Test
    fun `should call addFavorite`() = runTest {
        val fav = Favorite(1, "Latte", null, 22.0, true)
        useCase(fav)
        verify(repo).addFavorite(fav)
    }
}