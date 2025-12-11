package com.example.brewbuddy.domain.usecase

import com.example.brewbuddy.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUserNameUseCase(private val repo : UserRepository) {
    suspend operator fun invoke(): Flow< String?>{
        return repo.getUserName()
    }

}