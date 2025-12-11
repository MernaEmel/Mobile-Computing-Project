package com.example.brewbuddy.data.repository.impl

import com.example.brewbuddy.data.local.database.dao.CoffeeDao
import com.example.brewbuddy.data.local.database.entities.CoffeeEntity
import com.example.brewbuddy.data.remote.api.CoffeeApiModel
import com.example.brewbuddy.data.remote.api.CoffeeApiService
import com.example.brewbuddy.data.remote.dto.CoffeeDto
import com.example.brewbuddy.domain.model.Coffee
import com.example.brewbuddy.domain.model.CoffeeCategory
import com.example.brewbuddy.domain.model.SampleCoffees
import com.example.brewbuddy.domain.repository.CoffeeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoffeeRepositoryImpl @Inject constructor(
    private val api: CoffeeApiService,
    private val dao: CoffeeDao
) : CoffeeRepository {
    override suspend fun getCoffeesByCategory(category: String): List<CoffeeEntity> =
        dao.getCoffeesByCategory(category)

    override suspend fun fetchHotCoffees(): List<CoffeeEntity> {
        return try {
            val data = api.getHotCoffees()
            val entities = data.map { it.toEntity("HOT") }
            dao.insertCoffees(entities)
            entities
        } catch (e: Exception) {
            if (dao.getCoffeeCount() == 0) {
                dao.insertCoffees(SampleCoffees.defaultCoffees)
                SampleCoffees.defaultCoffees
            } else {
                dao.getCoffeesByCategory("HOT")
            }
        }
    }

    override suspend fun fetchColdCoffees(): List<CoffeeEntity> {
        return try {
            val data = api.getIcedCoffees()
            val entities = data.map { it.toEntity("COLD") }
            dao.insertCoffees(entities)
            entities
        } catch (e: Exception) {
            if (dao.getCoffeeCount() == 0) {
                dao.insertCoffees(SampleCoffees.defaultCoffees)
                SampleCoffees.defaultCoffees
            } else {
                dao.getCoffeesByCategory("COLD")
            }
        }
    }

    override fun getCachedCoffees(): Flow<List<CoffeeEntity>> = dao.getAllCoffees()

    override fun getCoffeesByCategory(category: CoffeeCategory): Flow<List<Coffee>> =
        dao.getCoffeesByCategoryFlow(category.name).map { entities ->
            entities.map { it.toDomainModel() }
        }

    override fun getFavoriteCoffees(): Flow<List<Coffee>> =
        dao.getFavoriteCoffees().map { entities ->
            entities.map { it.toDomainModel() }
        }

    override fun searchCoffees(query: String): Flow<List<Coffee>> =
        dao.searchCoffees(query).map { entities ->
            entities.map { it.toDomainModel() }
        }

    override suspend fun refreshCoffees() {
        dao.clearAllCoffees()
        fetchHotCoffees()
        fetchColdCoffees()
    }

    override suspend fun toggleFavorite(coffeeId: Int, isFavorite: Boolean) {
        dao.updateFavoriteStatus(coffeeId, isFavorite)
    }

    override suspend fun getBestSellerCoffee(): Coffee {
        val coffees = dao.getCoffeesByCategory("HOT")
        val entity = if (coffees.isNotEmpty()) {
            coffees.random()
        } else {
            // Fallback to sample data
            SampleCoffees.defaultCoffees.random()
        }
        return entity.toDomainModel()
    }

    override suspend fun getWeekRecommendation(): List<Coffee> {
        val allCoffees = dao.getCoffeeCount()
        return if (allCoffees > 0) {
            val hotCoffees = dao.getCoffeesByCategory("HOT")
            val coldCoffees = dao.getCoffeesByCategory("COLD")
            val combined = (hotCoffees + coldCoffees).shuffled().take(8)
            combined.map { it.toDomainModel() }
        } else {
            // Fallback to sample data
            SampleCoffees.defaultCoffees.shuffled().take(8).map { it.toDomainModel() }
        }
    }

    // Extension functions for mapping
    private fun CoffeeApiModel.toEntity(category: String) = CoffeeEntity(
        id = id,
        title = title,
        description = description,
        imageUrl = image,
        ingredients = ingredients.joinToString(","),
        price = price,
        category = category,
        isFavorite = false
    )

    private fun CoffeeEntity.toDomainModel() = Coffee(
        id = id,
        title = title,
        description = description,
        ingredients = ingredients.split(",").map { it.trim() },
        image = imageUrl,
        price = price,
        category = if (category == "HOT") CoffeeCategory.HOT else CoffeeCategory.COLD,
        isFavorite = isFavorite
    )
}
