package com.example.brewbuddy.domain.usecase

import com.example.brewbuddy.domain.repository.UserRepository

class SaveUserNameUseCase(private val repo: UserRepository) {
    suspend operator fun invoke(name: String){
        repo.saveUserName(name)
    }
}