package com.example.brewbuddy

import com.example.brewbuddy.domain.model.Coffee
import com.example.brewbuddy.domain.model.CoffeeCategory
import com.example.brewbuddy.domain.repository.CoffeeRepository
import com.example.brewbuddy.domain.usecase.home.GetBestSellerCoffeeUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetBestSellerCoffeeUseCaseTest {

    private val repo = mock<CoffeeRepository>()
    private val useCase = GetBestSellerCoffeeUseCase(repo)

    @Test
    fun `should return best seller coffee`() = runTest {
        val coffee = Coffee(1, "Latte", "desc", listOf("Coffee", "Milk"), "", 20.0, CoffeeCategory.HOT)
        whenever(repo.getBestSellerCoffee()).thenReturn(coffee)

        val result = useCase()
        assertEquals(coffee, result)
    }
}