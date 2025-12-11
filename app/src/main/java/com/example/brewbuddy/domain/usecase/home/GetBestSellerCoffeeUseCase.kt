package com.example.brewbuddy.domain.usecase.home

import com.example.brewbuddy.domain.model.Coffee
import com.example.brewbuddy.domain.repository.CoffeeRepository
import javax.inject.Inject

class GetBestSellerCoffeeUseCase @Inject constructor(
    private val coffeeRepository: CoffeeRepository
) {
    suspend operator fun invoke(): Coffee {
        return coffeeRepository.getBestSellerCoffee()
    }
}