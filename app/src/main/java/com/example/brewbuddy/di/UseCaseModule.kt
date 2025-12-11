package com.example.brewbuddy.di

import com.example.brewbuddy.domain.repository.CoffeeRepository
import com.example.brewbuddy.domain.repository.FavoritesRepository
import com.example.brewbuddy.domain.usecase.GetCoffeesByCategoryUseCase
import com.example.brewbuddy.domain.usecase.favorites.AddFavoriteUseCase
import com.example.brewbuddy.domain.usecase.favorites.FavoriteUseCase
import com.example.brewbuddy.domain.usecase.favorites.GetFavoriteUseCase
import com.example.brewbuddy.domain.usecase.favorites.RemoveFavoriteUseCase
import com.example.brewbuddy.domain.usecase.home.GetBestSellerCoffeeUseCase
import com.example.brewbuddy.domain.usecase.home.GetWeekRecommendationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    @ViewModelScoped
    fun provideGetCoffeesByCategoryUseCase(repository: CoffeeRepository): GetCoffeesByCategoryUseCase{
        return GetCoffeesByCategoryUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun  provideFavoriteUseCase(
        addFavoriteUseCase :AddFavoriteUseCase,
        removeFavoriteUseCase :RemoveFavoriteUseCase ,
        getFavoriteUseCase : GetFavoriteUseCase
    ): FavoriteUseCase{
        return FavoriteUseCase(
            addFavoriteUseCase =addFavoriteUseCase,
            removeFavoriteUseCase = removeFavoriteUseCase ,
            getFavoriteUseCase = getFavoriteUseCase ,
        )
    }

    @Provides
    @ViewModelScoped
    fun provideGetBestSellerCoffeeUseCase(repository: CoffeeRepository): GetBestSellerCoffeeUseCase {
        return GetBestSellerCoffeeUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetWeekRecommendationUseCase(repository: CoffeeRepository): GetWeekRecommendationUseCase {
        return GetWeekRecommendationUseCase(repository)
    }
}
