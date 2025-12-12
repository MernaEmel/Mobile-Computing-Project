package com.example.brewbuddy

import com.example.brewbuddy.domain.repository.UserRepository
import com.example.brewbuddy.domain.usecase.DeleteUserNameUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class DeleteUserNameUseCaseTest {

    private val repo = mock<UserRepository>()
    private val useCase = DeleteUserNameUseCase(repo)

    @Test
    fun `should call delete on repository`() = runTest {
        useCase()
        verify(repo).deleteUserName()
    }
}