package com.example.pizzapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pizzapp.repositories.OrderRepository

class OrderViewModelFactory(
    private val repository: OrderRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderViewModel::class.java)) {
            return requireNotNull(modelClass.cast(OrderViewModel(repository)))
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}