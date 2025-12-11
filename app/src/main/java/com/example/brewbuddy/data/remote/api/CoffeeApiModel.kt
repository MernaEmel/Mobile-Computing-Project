package com.example.brewbuddy.data.remote.api

data class CoffeeApiModel(
    val id: Int,
    val title: String,
    val description: String,
    val ingredients: List<String>,
    val image: String
) {
    val price: Double
        get() = (3 + (id % 5)) + 0.99
}

