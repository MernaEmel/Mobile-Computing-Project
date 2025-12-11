package com.example.brewbuddy

import com.example.brewbuddy.domain.repository.UserRepository
import com.example.brewbuddy.domain.usecase.GetUserNameUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class FakeUserRepo : UserRepository {
    private var savedName: String? = null

    override suspend fun saveUserName(name: String) {
        savedName = name
    }

    override fun getUserName(): Flow<String?> {
        return flowOf(savedName)
    }

    override suspend fun deleteUserName() {
        savedName = null
    }
}

class UserRepositoryJUnitTest {

    @Test
    fun `use case should return username using fake repo`() = runBlocking {
        // Arrange
        val fakeRepo = FakeUserRepo()
        fakeRepo.saveUserName("Merna")

        val useCase = GetUserNameUseCase(fakeRepo)

        // Act
        var result: String? = null
        useCase().collect {
            result = it
        }

        // Assert
        assertEquals("Merna", result)
    }
}
