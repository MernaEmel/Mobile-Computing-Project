package com.example.brewbuddy.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun saveUserName(name: String)
     fun  getUserName(): Flow<String?>

    suspend fun deleteUserName()
}