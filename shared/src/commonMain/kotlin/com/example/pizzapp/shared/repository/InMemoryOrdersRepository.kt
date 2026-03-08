package com.example.pizzapp.shared.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class InMemoryOrdersRepository : OrdersRepository {

    private val ordersFlow = MutableStateFlow<List<SavedOrder>>(emptyList())
    private var nextOrderId = 1

    override val orderHistory: Flow<List<SavedOrder>> = ordersFlow.asStateFlow()

    override suspend fun saveOrder(
        date: Long,
        totalPrice: Double,
        items: List<SavedOrderItem>
    ) {
        val newOrder = SavedOrder(
            id = nextOrderId++,
            date = date,
            totalPrice = totalPrice,
            items = items
        )

        ordersFlow.value = listOf(newOrder) + ordersFlow.value
    }
}
