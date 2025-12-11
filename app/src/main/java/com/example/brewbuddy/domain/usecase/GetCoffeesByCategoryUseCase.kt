package com.example.brewbuddy.domain.usecase

import com.example.brewbuddy.domain.model.Coffee
import com.example.brewbuddy.domain.model.CoffeeCategory
import com.example.brewbuddy.domain.repository.CoffeeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCoffeesByCategoryUseCase @Inject constructor(
    private val coffeeRepository: CoffeeRepository
) {
    // TODO: Implement the use case
    operator fun invoke(category: CoffeeCategory): Flow<List<Coffee>> {
        TODO("Not yet implemented")
    }
}
