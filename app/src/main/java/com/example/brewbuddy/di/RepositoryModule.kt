package com.example.brewbuddy.di

import com.example.brewbuddy.data.repository.impl.FavoritesRepositoryImpl
import com.example.brewbuddy.data.repository.impl.CoffeeRepositoryImpl
import com.example.brewbuddy.data.repository.impl.UserRepositoryImpl
import com.example.brewbuddy.data.repository.impl.OrderHistoryRepositoryImp
import com.example.brewbuddy.domain.repository.CoffeeRepository
import com.example.brewbuddy.domain.repository.UserRepository
import com.example.brewbuddy.domain.usecase.DeleteUserNameUseCase
import com.example.brewbuddy.domain.usecase.GetUserNameUseCase
import com.example.brewbuddy.domain.usecase.SaveUserNameUseCase
import com.example.brewbuddy.domain.repository.FavoritesRepository
import com.example.brewbuddy.domain.repository.OrderHistoryRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCoffeeRepository(
        impl: CoffeeRepositoryImpl
    ): CoffeeRepository
    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindFavoritesRepository(
        impl: FavoritesRepositoryImpl
    ): FavoritesRepository

    @Binds
    @Singleton
    abstract fun bindOrderHistoryRepository(
        orderHistoryRepositoryImp: OrderHistoryRepositoryImp
    ): OrderHistoryRepository

}
