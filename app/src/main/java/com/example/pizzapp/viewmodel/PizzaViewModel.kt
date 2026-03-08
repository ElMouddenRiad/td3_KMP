package com.example.pizzapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.pizzapp.data.DataSource
import com.example.pizzapp.model.Pizza

class PizzaViewModel : ViewModel() {

    private val dataSource = DataSource()

    val pizzas: List<Pizza> = dataSource.loadPizzas()

    fun getPizzaById(id: Int): Pizza {
        return pizzas.first { it.id == id }
    }
}