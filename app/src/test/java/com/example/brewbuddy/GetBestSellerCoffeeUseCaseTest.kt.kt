package com.example.brewbuddy

import com.example.brewbuddy.domain.model.Coffee
import com.example.brewbuddy.domain.repository.CoffeeRepository
import com.example.brewbuddy.domain.usecase.home.GetBestSellerCoffeeUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class `GetBestSellerCoffeeUseCaseTest.kt` {

    private val repo = mock<CoffeeRepository>()
    private val useCase = GetBestSellerCoffeeUseCase(repo)

    @Test
    fun `should return best seller coffee`() = runTest {
        val coffee = Coffee(1, "Latte", 20.0)
        whenever(repo.getBestSellerCoffee()).thenReturn(coffee)

        val result = useCase()

        assertEquals(coffee, result)
    }
}