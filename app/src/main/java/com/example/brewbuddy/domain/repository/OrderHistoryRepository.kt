package com.example.brewbuddy.domain.repository

import com.example.brewbuddy.data.local.database.entities.OrderHistory

interface OrderHistoryRepository {
    suspend fun addOrder(order: OrderHistory)
    suspend fun getRecentOrders(): List<OrderHistory>
    suspend fun getPastOrders(): List<OrderHistory>
    suspend fun getOrdersByCoffeeId(coffeeId: Int): List<OrderHistory>
}