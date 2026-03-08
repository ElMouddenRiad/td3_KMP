package com.example.pizzapp.desktop

import com.example.pizzapp.shared.repository.OrdersRepository
import com.example.pizzapp.shared.repository.SavedOrder
import com.example.pizzapp.shared.repository.SavedOrderItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class DesktopFileOrdersRepository : OrdersRepository {

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    private val storageFile: File by lazy {
        val dir = File(System.getProperty("user.home"), ".pizzapp")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        File(dir, "orders.json")
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
        return runCatching {
            if (!storageFile.exists()) {
                emptyList()
            } else {
                val content = storageFile.readText()
                if (content.isBlank()) emptyList() else json.decodeFromString<List<SavedOrder>>(content)
            }
        }.getOrElse { emptyList() }
    }

    private fun persistOrders(orders: List<SavedOrder>) {
        runCatching {
            storageFile.writeText(json.encodeToString(orders))
        }
    }
}
