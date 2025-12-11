package com.example.brewbuddy.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CoffeeDto(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("title")
    val title: String,
    
    @SerializedName("description")
    val description: String,
    
    @SerializedName("ingredients")
    val ingredients: List<String>,
    
    @SerializedName("image")
    val image: String
)
