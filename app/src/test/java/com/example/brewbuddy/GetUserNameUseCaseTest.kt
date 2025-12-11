package com.example.brewbuddy

import com.example.brewbuddy.domain.repository.UserRepository
import com.example.brewbuddy.domain.usecase.GetUserNameUseCase
import com.example.brewbuddy.domain.usecase.SaveUserNameUseCase
import com.example.brewbuddy.domain.usecase.DeleteUserNameUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class UserRepositoryUseCasesTest {

    private lateinit var repo: UserRepository
    private lateinit var getUserNameUseCase: GetUserNameUseCase
    private lateinit var saveUserNameUseCase: SaveUserNameUseCase
    private lateinit var deleteUserNameUseCase: DeleteUserNameUseCase

    @Before
    fun setup() {
        repo = mock()
        getUserNameUseCase = GetUserNameUseCase(repo)
        saveUserNameUseCase = SaveUserNameUseCase(repo)
        deleteUserNameUseCase = DeleteUserNameUseCase(repo)
    }

    @Test
    fun `getUserName should return username from repository`() = runTest {
        val expectedName = "Merna"
        whenever(repo.getUserName()).thenReturn(flowOf(expectedName))

        val resultFlow = getUserNameUseCase()
        var actualValue: String? = null
        resultFlow.collect { value ->
            actualValue = value
        }

        assertEquals(expectedName, actualValue)
    }

    @Test
    fun `saveUserName should call repository saveUserName`() = runTest {
        val nameToSave = "Merna"

        saveUserNameUseCase(nameToSave)

        verify(repo).saveUserName(nameToSave) // checks repo method was called
    }

    @Test
    fun `deleteUserName should call repository deleteUserName`() = runTest {
        deleteUserNameUseCase()

        verify(repo).deleteUserName() // checks repo method was called
    }
}
