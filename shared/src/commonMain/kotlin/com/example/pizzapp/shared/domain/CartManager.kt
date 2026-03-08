package com.example.pizzapp.shared.domain

import com.example.pizzapp.shared.model.Pizza

class CartManager {

    private val items = mutableListOf<CartItem>()

    fun getItems(): List<CartItem> = items.toList()

    fun addPizza(pizza: Pizza, extraCheese: Int) {
        val existingIndex = items.indexOfFirst {
            it.pizza.id == pizza.id && it.extraCheese == extraCheese
        }

        if (existingIndex >= 0) {
            val existing = items[existingIndex]
            items[existingIndex] = existing.copy(quantity = existing.quantity + 1)
        } else {
            items.add(CartItem(pizza = pizza, extraCheese = extraCheese, quantity = 1))
        }
    }

    fun increaseQuantity(pizzaId: Int, extraCheese: Int) {
        val index = items.indexOfFirst { it.pizza.id == pizzaId && it.extraCheese == extraCheese }
        if (index < 0) return

        val item = items[index]
        items[index] = item.copy(quantity = item.quantity + 1)
    }

    fun decreaseQuantity(pizzaId: Int, extraCheese: Int) {
        val index = items.indexOfFirst { it.pizza.id == pizzaId && it.extraCheese == extraCheese }
        if (index < 0) return

        val item = items[index]
        if (item.quantity > 1) {
            items[index] = item.copy(quantity = item.quantity - 1)
        } else {
            items.removeAt(index)
        }
    }

    fun clear() {
        items.clear()
    }

    fun totalPrice(): Double {
        val lines = items.map {
            OrderLine(
                basePrice = it.pizza.price,
                extraCheese = it.extraCheese,
                quantity = it.quantity
            )
        }

        return OrderPricing.calculateTotal(lines)
    }
}
