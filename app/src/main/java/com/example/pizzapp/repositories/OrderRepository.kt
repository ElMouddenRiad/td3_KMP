package com.example.pizzapp.repositories

import com.example.pizzapp.dao.OrderDao
import com.example.pizzapp.data.OrderEntity
import com.example.pizzapp.data.OrderItemEntity
import com.example.pizzapp.shared.repository.OrdersRepository
import com.example.pizzapp.shared.repository.SavedOrder
import com.example.pizzapp.shared.repository.SavedOrderItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OrderRepository(private val orderDao: OrderDao) : OrdersRepository {

    val allOrdersWithItems = orderDao.getAllOrdersWithItems()

    suspend fun insertOrderWithItems(order: OrderEntity, items: List<OrderItemEntity>) {
        orderDao.insertOrderWithItems(order, items)
    }

    override val orderHistory: Flow<List<SavedOrder>> = orderDao.getAllOrdersWithItems().map { orders ->
        orders.map { orderWithItems ->
            SavedOrder(
                id = orderWithItems.order.id,
                date = orderWithItems.order.date,
                totalPrice = orderWithItems.order.totalPrice,
                items = orderWithItems.items.map { item ->
                    SavedOrderItem(
                        pizzaId = item.pizzaId,
                        pizzaName = item.pizzaName,
                        basePrice = item.basePrice,
                        extraCheese = item.extraCheese,
                        quantity = item.quantity
                    )
                }
            )
        }
    }

    override suspend fun saveOrder(date: Long, totalPrice: Double, items: List<SavedOrderItem>) {
        val order = OrderEntity(
            date = date,
            totalPrice = totalPrice
        )

        val orderItems = items.map {
            OrderItemEntity(
                orderId = 0,
                pizzaId = it.pizzaId,
                pizzaName = it.pizzaName,
                basePrice = it.basePrice,
                extraCheese = it.extraCheese,
                quantity = it.quantity
            )
        }

        orderDao.insertOrderWithItems(order, orderItems)
    }
}