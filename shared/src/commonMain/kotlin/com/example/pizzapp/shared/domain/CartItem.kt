package com.example.pizzapp.shared.domain

import com.example.pizzapp.shared.model.Pizza

data class CartItem(
    val pizza: Pizza,
    val extraCheese: Int,
    val quantity: Int = 1
)
