package com.example.brewbuddy.domain.usecase.home

import com.example.brewbuddy.domain.model.Coffee
import com.example.brewbuddy.domain.repository.CoffeeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetWeekRecommendationUseCase @Inject constructor(
    private val repository: CoffeeRepository
) {
    suspend operator fun invoke(): List<Coffee> {
        return repository.getWeekRecommendation()
    }
}