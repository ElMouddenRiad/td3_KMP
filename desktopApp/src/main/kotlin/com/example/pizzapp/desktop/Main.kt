package com.example.pizzapp.desktop

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.pizzapp.persistence.SqlDelightOrdersRepository
import com.example.pizzapp.persistence.db.PizzAppDatabase
import com.example.pizzapp.shared.domain.OrderService
import com.example.pizzapp.shared.ui.PizzAppSharedScreen
import java.io.File

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "PizzApp"
    ) {
        val repository = remember {
            val appDir = File(System.getProperty("user.home"), ".pizzapp")
            if (!appDir.exists()) {
                appDir.mkdirs()
            }

            val databaseFile = File(appDir, "pizzapp.db")
            val isNewDatabase = !databaseFile.exists()
            val driver = JdbcSqliteDriver(url = "jdbc:sqlite:${databaseFile.absolutePath}")
            if (isNewDatabase) {
                PizzAppDatabase.Schema.create(driver)
            }
            val database = PizzAppDatabase(driver)

            SqlDelightOrdersRepository(database)
        }
        val orderService = remember { OrderService(repository) }
        PizzAppSharedScreen(
            orderService = orderService,
            title = "PizzApp",
            nowMillisProvider = { System.currentTimeMillis() }
        )
    }
}
