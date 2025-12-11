package com.example.brewbuddy

import com.example.brewbuddy.domain.model.Favorite
import com.example.brewbuddy.domain.repository.FavoritesRepository
import com.example.brewbuddy.domain.usecase.favorites.GetFavoriteUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class `GetFavoriteUseCaseTest.kt` {

    private val repo = mock<FavoritesRepository>()
    private val useCase = GetFavoriteUseCase(repo)

    @Test
    fun `should return favorite list`() = runTest {
        val list = listOf(Favorite(1, "Mocha"))
        whenever(repo.getFavorites()).thenReturn(flowOf(list))

        var result: List<Favorite>? = null
        useCase().collect { result = it }

        assertEquals(list, result)
    }
}