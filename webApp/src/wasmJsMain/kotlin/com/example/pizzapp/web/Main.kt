package com.example.pizzapp.web

import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.example.pizzapp.shared.domain.OrderService
import com.example.pizzapp.shared.ui.PizzAppSharedScreen

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow(title = "PizzApp") {
        val repository = remember { WebLocalStorageOrdersRepository() }
        val orderService = remember { OrderService(repository) }
        PizzAppSharedScreen(orderService = orderService, title = "PizzApp")
    }
}
