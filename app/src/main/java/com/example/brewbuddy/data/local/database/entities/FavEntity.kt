package com.example.brewbuddy.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favorites")
data class FavEntity(
    @PrimaryKey val coffeeId: Int,
    val title: String,
    val image: String?,
    val priceCents: Double,
    val isHot: Boolean
)