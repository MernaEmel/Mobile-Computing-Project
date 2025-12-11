package com.example.brewbuddy.di

import com.example.brewbuddy.domain.repository.UserRepository
import com.example.brewbuddy.domain.usecase.DeleteUserNameUseCase
import com.example.brewbuddy.domain.usecase.GetUserNameUseCase
import com.example.brewbuddy.domain.usecase.SaveUserNameUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UserUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetUserNameUseCase(userRepository: UserRepository): GetUserNameUseCase {
        return GetUserNameUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideSaveUserNameUseCase(userRepository: UserRepository): SaveUserNameUseCase {
        return SaveUserNameUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideDeleteUserNameUseCase(userRepository: UserRepository): DeleteUserNameUseCase {
        return DeleteUserNameUseCase(userRepository)
    }
}
