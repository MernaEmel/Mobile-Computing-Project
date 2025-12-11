package com.example.brewbuddy.data.repository.impl

import com.example.brewbuddy.data.local.database.dao.OrderHistoryDao
import com.example.brewbuddy.data.local.database.entities.OrderHistory
import com.example.brewbuddy.domain.repository.OrderHistoryRepository
import javax.inject.Inject

class OrderHistoryRepositoryImp @Inject constructor(
    private val orderHistoryDao: OrderHistoryDao
): OrderHistoryRepository {
    override suspend fun addOrder(order: OrderHistory) {
        orderHistoryDao.insertOrder(order)
    }

    override suspend fun getRecentOrders(): List<OrderHistory> {
        return orderHistoryDao.getRecentOrders()
    }

    override suspend fun getPastOrders(): List<OrderHistory> {
        return orderHistoryDao.getPastOrders()
    }

    override suspend fun getOrdersByCoffeeId(coffeeId: Int): List<OrderHistory> {
        return orderHistoryDao.getOrdersByCoffeeId(coffeeId)
    }
}