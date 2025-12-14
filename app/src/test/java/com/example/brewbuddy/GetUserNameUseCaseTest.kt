package com.example.brewbuddy

import com.example.brewbuddy.domain.repository.UserRepository
import com.example.brewbuddy.domain.usecase.GetUserNameUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetUserNameUseCaseTest {

    private val repo = mock<UserRepository>()
    private val useCase = GetUserNameUseCase(repo)

    @Test
    fun `should return username from repository`() = runTest {
        whenever(repo.getUserName()).thenReturn(flowOf("Merna"))

        val result = useCase().first()
        assertEquals("Merna", result)
    }
}