package com.example.pizzapp.shared.domain

import com.example.pizzapp.shared.model.Pizza
import com.example.pizzapp.shared.repository.OrdersRepository
import com.example.pizzapp.shared.repository.SavedOrder
import com.example.pizzapp.shared.repository.SavedOrderItem
import kotlinx.coroutines.flow.Flow
import kotlin.math.round

class OrderService(
    private val ordersRepository: OrdersRepository,
    private val cartManager: CartManager = CartManager()
) {

    val orderHistory: Flow<List<SavedOrder>> = ordersRepository.orderHistory

    fun addPizza(pizza: Pizza, extraCheese: Int) {
        cartManager.addPizza(pizza, extraCheese)
    }

    fun increaseQuantity(pizzaId: Int, extraCheese: Int) {
        cartManager.increaseQuantity(pizzaId, extraCheese)
    }

    fun decreaseQuantity(pizzaId: Int, extraCheese: Int) {
        cartManager.decreaseQuantity(pizzaId, extraCheese)
    }

    fun clearCart() {
        cartManager.clear()
    }

    fun cartItems(): List<CartItem> = cartManager.getItems()

    fun totalPrice(): Double = cartManager.totalPrice()

    fun roundedTotalPrice(): Double = round(totalPrice() * 100.0) / 100.0

    suspend fun checkout(nowMillis: Long) {
        val items = cartManager.getItems()
        if (items.isEmpty()) return

        val savedItems = items.map {
            SavedOrderItem(
                pizzaId = it.pizza.id,
                pizzaName = it.pizza.name,
                basePrice = it.pizza.price,
                extraCheese = it.extraCheese,
                quantity = it.quantity
            )
        }

        ordersRepository.saveOrder(
            date = nowMillis,
            totalPrice = roundedTotalPrice(),
            items = savedItems
        )

        cartManager.clear()
    }
}
