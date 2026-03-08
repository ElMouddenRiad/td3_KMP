package com.example.pizzapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val date: Long,
    val totalPrice: Double
)
