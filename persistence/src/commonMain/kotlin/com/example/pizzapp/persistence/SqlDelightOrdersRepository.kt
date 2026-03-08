package com.example.pizzapp.persistence

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.pizzapp.persistence.db.PizzAppDatabase
import com.example.pizzapp.shared.repository.OrdersRepository
import com.example.pizzapp.shared.repository.SavedOrder
import com.example.pizzapp.shared.repository.SavedOrderItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SqlDelightOrdersRepository(
    private val database: PizzAppDatabase
) : OrdersRepository {

    override val orderHistory: Flow<List<SavedOrder>> =
        database.pizzAppDatabaseQueries
            .selectOrderHistory()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { rows ->
                rows.groupBy { it.id }.map { (orderId, orderRows) ->
                    val first = orderRows.first()
                    SavedOrder(
                        id = orderId.toInt(),
                        date = first.date,
                        totalPrice = first.total_price,
                        items = orderRows.mapNotNull { row ->
                            val pizzaId = row.pizza_id ?: return@mapNotNull null
                            val pizzaName = row.pizza_name ?: return@mapNotNull null
                            val basePrice = row.base_price ?: return@mapNotNull null
                            val extraCheese = row.extra_cheese ?: return@mapNotNull null
                            val quantity = row.quantity ?: return@mapNotNull null

                            SavedOrderItem(
                                pizzaId = pizzaId.toInt(),
                                pizzaName = pizzaName,
                                basePrice = basePrice,
                                extraCheese = extraCheese.toInt(),
                                quantity = quantity.toInt()
                            )
                        }
                    )
                }
            }

    override suspend fun saveOrder(date: Long, totalPrice: Double, items: List<SavedOrderItem>) {
        database.transaction {
            database.pizzAppDatabaseQueries.insertOrder(
                date = date,
                total_price = totalPrice
            )

            val orderId = database.pizzAppDatabaseQueries.lastInsertRowId().executeAsOne()

            items.forEach { item ->
                database.pizzAppDatabaseQueries.insertOrderItem(
                    order_id = orderId,
                    pizza_id = item.pizzaId.toLong(),
                    pizza_name = item.pizzaName,
                    base_price = item.basePrice,
                    extra_cheese = item.extraCheese.toLong(),
                    quantity = item.quantity.toLong()
                )
            }
        }
    }
}
