package com.example.pizzapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizzapp.data.OrderEntity
import com.example.pizzapp.data.OrderItemEntity
import com.example.pizzapp.model.OrderItem
import com.example.pizzapp.model.Pizza
import com.example.pizzapp.repositories.OrderRepository
import kotlinx.coroutines.launch
import kotlin.math.round

class OrderViewModel(
    private val repository: OrderRepository
) : ViewModel() {

    companion object {
        private const val EXTRA_CHEESE_UNIT_PRICE = 0.01
    }

    private val _order = mutableStateListOf<OrderItem>()
    val order: SnapshotStateList<OrderItem> = _order

    val orderHistory = repository.allOrdersWithItems

    fun saveOrder() {
        if (_order.isEmpty()) return

        val total = getTotalPrice()
        val roundedTotal = round(total * 100) / 100

        val entity = OrderEntity(
            date = System.currentTimeMillis(),
            totalPrice = roundedTotal
        )

        val items = _order.map { item ->
            OrderItemEntity(
                orderId = 0,
                pizzaId = item.pizza.id,
                pizzaName = item.pizza.name,
                basePrice = item.pizza.price,
                extraCheese = item.extraCheese,
                quantity = item.quantity
            )
        }

        viewModelScope.launch {
            repository.insertOrderWithItems(entity, items)
        }
    }


    fun addPizza(pizza: Pizza, extraCheese: Int) {
        val existingItem = _order.find {
            it.pizza.id == pizza.id &&
                    it.extraCheese == extraCheese
        }

        if (existingItem != null) {
            existingItem.quantity++
        } else {
            _order.add(OrderItem(pizza, extraCheese))
        }
    }

    fun getTotalPrice(): Double {
        return _order.sumOf {
            val extraCheesePrice = it.extraCheese * EXTRA_CHEESE_UNIT_PRICE
            (it.pizza.price + extraCheesePrice) * it.quantity
        }
    }

    fun clearOrder() {
        _order.clear()
    }

    fun increaseQuantity(item: OrderItem) {
        item.quantity++
    }

    fun decreaseQuantity(item: OrderItem) {
        if (item.quantity > 1) {
            item.quantity--
        } else {
            _order.remove(item)
        }
    }
}