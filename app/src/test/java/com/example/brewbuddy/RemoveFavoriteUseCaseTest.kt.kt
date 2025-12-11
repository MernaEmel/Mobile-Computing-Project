package com.example.brewbuddy

import com.example.brewbuddy.domain.repository.FavoritesRepository
import com.example.brewbuddy.domain.usecase.favorites.RemoveFavoriteUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class `RemoveFavoriteUseCaseTest.kt` {

    private val repo = mock<FavoritesRepository>()
    private val useCase = RemoveFavoriteUseCase(repo)

    @Test
    fun `should remove favorite by id`() = runTest {
        useCase(10)
        verify(repo).removeFavorite(10)
    }
}