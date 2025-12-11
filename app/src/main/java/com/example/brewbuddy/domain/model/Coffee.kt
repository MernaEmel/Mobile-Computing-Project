package com.example.brewbuddy.domain.model

import android.os.Parcelable
import com.example.brewbuddy.data.remote.dto.CoffeeDto
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class Coffee(
    val id: Int,
    val title: String,
    val description: String,
    val ingredients: List<String>,
    val image: String,
    val price: Double,
    val category: CoffeeCategory,
    val isFavorite: Boolean = false
) : Parcelable {
    companion object {
        fun fromDto(dto: CoffeeDto, category: CoffeeCategory): Coffee {
            return Coffee(
                id = dto.id,
                title = dto.title,
                description = dto.description,
                ingredients = dto.ingredients,
                image = dto.image,
                price = 50.00,
                category = category
            )
        }
    }
}

enum class CoffeeCategory {
    HOT, COLD
}
