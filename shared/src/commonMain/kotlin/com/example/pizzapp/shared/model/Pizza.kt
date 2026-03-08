package com.example.pizzapp.shared.model

data class Pizza(
    val id: Int,
    val name: String,
    val price: Double,
    val ingredients: List<String>,
    val image: Int
)
