package com.example.brewbuddy

import com.example.brewbuddy.domain.model.Coffee
import com.example.brewbuddy.domain.repository.CoffeeRepository
import com.example.brewbuddy.domain.usecase.home.GetWeekRecommendationUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetWeekRecommendationUseCaseTest {

    private val repo = mock<CoffeeRepository>()
    private val useCase = GetWeekRecommendationUseCase(repo)

    @Test
    fun `should return weekly recommendations`() = runTest {
        val list = listOf(
            Coffee(1, "Mocha", "desc", listOf("Coffee"), "", 20.0, CoffeeCategory.HOT),
            Coffee(2, "Latte", "desc", listOf("Coffee", "Milk"), "", 22.0, CoffeeCategory.COLD)
        )

        whenever(repo.getWeekRecommendation()).thenReturn(list)

        val result = useCase()
        assertEquals(list, result)
    }
}