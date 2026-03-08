package com.example.pizzapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.pizzapp.data.OrderEntity
import com.example.pizzapp.data.OrderItemEntity
import com.example.pizzapp.data.OrderWithItems
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    @Insert
    suspend fun insertOrder(order: OrderEntity): Long

    @Insert
    suspend fun insertOrderItems(items: List<OrderItemEntity>)

    @Transaction
    suspend fun insertOrderWithItems(order: OrderEntity, items: List<OrderItemEntity>) {
        val newOrderId = insertOrder(order).toInt()

        val itemsWithOrderId = items.map { it.copy(orderId = newOrderId) }
        insertOrderItems(itemsWithOrderId)
    }

    @Query("SELECT * FROM orders ORDER BY date DESC")
    fun getAllOrders(): Flow<List<OrderEntity>>

    @Transaction
    @Query("SELECT * FROM orders ORDER BY date DESC")
    fun getAllOrdersWithItems(): Flow<List<OrderWithItems>>
}
