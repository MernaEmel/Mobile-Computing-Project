package com.example.brewbuddy.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.brewbuddy.data.local.database.entities.OrderHistory

@Dao
interface OrderHistoryDao {
    @Insert
    suspend fun insertOrder(order: OrderHistory)

    @Query("SELECT * FROM OrderHistory ORDER BY date DESC") // Recent: descending
    suspend fun getPastOrders(): List<OrderHistory>

    @Query("SELECT * FROM OrderHistory ORDER BY date ASC") // Past: ascending
    suspend fun getRecentOrders(): List<OrderHistory>

    @Query("SELECT * FROM OrderHistory WHERE coffeeId = :coffeeId")
    suspend fun getOrdersByCoffeeId(coffeeId: Int): List<OrderHistory>
}