package com.example.pizzapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.pizzapp.persistence.SqlDelightOrdersRepository
import com.example.pizzapp.persistence.db.PizzAppDatabase
import com.example.pizzapp.shared.domain.OrderService
import com.example.pizzapp.shared.ui.PizzAppSharedScreen

@Composable
fun MyApp() {
    val context = LocalContext.current

    val database = remember {
        val driver = AndroidSqliteDriver(
            schema = PizzAppDatabase.Schema,
            context = context,
            name = "pizzapp.db"
        )
        PizzAppDatabase(driver)
    }

    val repository = remember {
        SqlDelightOrdersRepository(database)
    }

    val orderService = remember { OrderService(repository) }

    PizzAppSharedScreen(
        orderService = orderService,
        title = "PizzApp",
        nowMillisProvider = { System.currentTimeMillis() }
    )
}