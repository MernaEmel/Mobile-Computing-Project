package com.example.brewbuddy

import com.example.brewbuddy.domain.model.Favorite
import com.example.brewbuddy.domain.repository.FavoritesRepository
import com.example.brewbuddy.domain.usecase.favorites.GetFavoriteUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.flow.first
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetFavoriteUseCaseTest {

    private val repo = mock<FavoritesRepository>()
    private val useCase = GetFavoriteUseCase(repo)

    @Test
    fun `should return favorite list`() = runTest {
        val list = listOf(Favorite(1, "Mocha", null, 20.0, true))
        whenever(repo.getFavorites()).thenReturn(flowOf(list))

        val result = useCase().first()
        assertEquals(list, result)
    }
}