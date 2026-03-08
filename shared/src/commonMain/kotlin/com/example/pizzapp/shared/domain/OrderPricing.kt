package com.example.pizzapp.shared.domain

data class OrderLine(
    val basePrice: Double,
    val extraCheese: Int,
    val quantity: Int
)

object OrderPricing {
    private const val EXTRA_CHEESE_UNIT_PRICE = 0.01

    fun calculateTotal(lines: List<OrderLine>): Double {
        return lines.sumOf { line ->
            val extraCheesePrice = line.extraCheese * EXTRA_CHEESE_UNIT_PRICE
            (line.basePrice + extraCheesePrice) * line.quantity
        }
    }
}
