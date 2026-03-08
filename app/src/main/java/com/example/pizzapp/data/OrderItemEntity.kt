package com.example.pizzapp.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "order_items",
    foreignKeys = [
        ForeignKey(
            entity = OrderEntity::class,
            parentColumns = ["id"],
            childColumns = ["orderId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["orderId"])]
)
data class OrderItemEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val orderId: Int,
    val pizzaId: Int,
    val pizzaName: String,
    val basePrice: Double,
    val extraCheese: Int,
    val quantity: Int
)