package com.example.pizzapp.shared.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable

@Serializable
data class SavedOrderItem(
    val pizzaId: Int,
    val pizzaName: String,
    val basePrice: Double,
    val extraCheese: Int,
    val quantity: Int
)

@Serializable
data class SavedOrder(
    val id: Int,
    val date: Long,
    val totalPrice: Double,
    val items: List<SavedOrderItem>
)

interface OrdersRepository {
    val orderHistory: kotlinx.coroutines.flow.Flow<List<SavedOrder>>

    suspend fun saveOrder(
        date: Long,
        totalPrice: Double,
        items: List<SavedOrderItem>
    )
}
