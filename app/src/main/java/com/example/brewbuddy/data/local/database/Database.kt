package com.example.brewbuddy.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.brewbuddy.data.local.database.dao.CoffeeDao
import com.example.brewbuddy.data.local.database.dao.FavoritesDao
import com.example.brewbuddy.data.local.database.entities.CoffeeEntity
import com.example.brewbuddy.data.local.database.dao.OrderHistoryDao
import com.example.brewbuddy.data.local.database.entities.FavEntity
import com.example.brewbuddy.data.local.database.entities.OrderHistory

@Database(
    entities = [FavEntity::class, OrderHistory::class , CoffeeEntity::class],
    version = 3,
    exportSchema = false
)
abstract class BrewBuddyDatabase : RoomDatabase() {
    abstract fun favDao() : FavoritesDao
    abstract fun orderHistoryDao() : OrderHistoryDao
    abstract fun coffeeDao(): CoffeeDao
}