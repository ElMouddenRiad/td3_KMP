package com.example.pizzapp.web

import com.example.pizzapp.shared.repository.OrdersRepository
import com.example.pizzapp.shared.repository.SavedOrder
import com.example.pizzapp.shared.repository.SavedOrderItem
import kotlinx.browser.window
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class WebLocalStorageOrdersRepository : OrdersRepository {

    private val storageKey = "pizzapp_orders"

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val ordersFlow = MutableStateFlow(loadOrders())

    override val orderHistory: Flow<List<SavedOrder>> = ordersFlow.asStateFlow()

    override suspend fun saveOrder(date: Long, totalPrice: Double, items: List<SavedOrderItem>) {
        val current = ordersFlow.value
        val nextId = (current.maxOfOrNull { it.id } ?: 0) + 1
        val next = listOf(
            SavedOrder(
                id = nextId,
                date = date,
                totalPrice = totalPrice,
                items = items
            )
        ) + current

        ordersFlow.value = next
        persistOrders(next)
    }

    private fun loadOrders(): List<SavedOrder> {
        val raw = window.localStorage.getItem(storageKey).orEmpty()
        if (raw.isBlank()) return emptyList()

        return runCatching {
            json.decodeFromString<List<SavedOrder>>(raw)
        }.getOrElse { emptyList() }
    }

    private fun persistOrders(orders: List<SavedOrder>) {
        runCatching {
            window.localStorage.setItem(storageKey, json.encodeToString(orders))
        }
    }
}
