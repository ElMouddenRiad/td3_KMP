package com.example.pizzapp.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class OrderItem(
    val pizza: Pizza,
    val extraCheese: Int
) {
    var quantity by mutableStateOf(1)
}