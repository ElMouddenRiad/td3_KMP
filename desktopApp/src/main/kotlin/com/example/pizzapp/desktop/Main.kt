package com.example.pizzapp.desktop

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.pizzapp.shared.domain.OrderService
import com.example.pizzapp.shared.ui.PizzAppSharedScreen

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "PizzApp"
    ) {
        val repository = remember { DesktopFileOrdersRepository() }
        val orderService = remember { OrderService(repository) }
        PizzAppSharedScreen(
            orderService = orderService,
            title = "PizzApp",
            nowMillisProvider = { System.currentTimeMillis() }
        )
    }
}
