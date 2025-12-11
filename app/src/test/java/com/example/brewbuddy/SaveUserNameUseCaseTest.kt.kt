package com.example.brewbuddy

import com.example.brewbuddy.domain.repository.UserRepository
import com.example.brewbuddy.domain.usecase.SaveUserNameUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class `SaveUserNameUseCaseTest.kt` {

    private val repo = mock<UserRepository>()
    private val useCase = SaveUserNameUseCase(repo)

    @Test
    fun `should call repo saveUserName`() = runTest {
        useCase("Merna")
        verify(repo).saveUserName("Merna")
    }
}