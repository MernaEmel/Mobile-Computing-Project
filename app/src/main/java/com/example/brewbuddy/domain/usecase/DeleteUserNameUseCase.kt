package com.example.brewbuddy.domain.usecase

import com.example.brewbuddy.domain.repository.UserRepository

class DeleteUserNameUseCase(private val repo: UserRepository) {
    suspend operator fun invoke(){
        repo.deleteUserName()
    }
}