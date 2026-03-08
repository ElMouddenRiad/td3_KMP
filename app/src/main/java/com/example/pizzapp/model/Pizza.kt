package com.example.pizzapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Pizza(
    val id: Int,
    val name: String,
    val price: Double,
    val ingredients: List<String>,
    val image: Int
)
