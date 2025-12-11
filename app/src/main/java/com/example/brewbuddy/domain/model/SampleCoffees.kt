package com.example.brewbuddy.domain.model

import com.example.brewbuddy.data.local.database.entities.CoffeeEntity

object SampleCoffees {
    val defaultCoffees = listOf(
        CoffeeEntity(
            id = 1,
            title = "Espresso",
            description = "Strong and bold hot coffee.",
            imageUrl = "",
            ingredients = "Espresso",
            price = 2.5,
            category = "HOT"
        ),
        CoffeeEntity(
            id = 2,
            title = "Cappuccino",
            description = "Espresso with steamed milk and foam.",
            imageUrl = "",
            ingredients = "Espresso, Milk, Foam",
            price = 3.5,
            category = "HOT"
        ),
        CoffeeEntity(
            id = 3,
            title = "Iced Latte",
            description = "Chilled espresso with milk and ice.",
            imageUrl = "",
            ingredients = "Espresso, Milk, Ice",
            price = 3.0,
            category = "COLD"
        )
    )
}