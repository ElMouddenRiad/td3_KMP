package com.example.pizzapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pizzapp.data.AppDatabase
import com.example.pizzapp.repositories.OrderRepository
import com.example.pizzapp.screens.DetailPizza
import com.example.pizzapp.screens.PizzaMenu
import com.example.pizzapp.screens.CaddyScreen
import com.example.pizzapp.screens.HistoryScreen
import com.example.pizzapp.screens.WelcomeScreen
import com.example.pizzapp.viewmodel.OrderViewModel
import com.example.pizzapp.viewmodel.OrderViewModelFactory
import com.example.pizzapp.viewmodel.PizzaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {

    val navController = rememberNavController()
    val context = LocalContext.current

    val pizzaViewModel: PizzaViewModel = viewModel()

    // ----- Room setup -----
    val database = remember {
        AppDatabase.getDatabase(context)
    }

    val repository = remember {
        OrderRepository(database.orderDao())
    }

    val orderViewModel: OrderViewModel = viewModel(
        factory = OrderViewModelFactory(repository)
    )
    // ----------------------

    Scaffold(

        topBar = {
            TopAppBar(
                title = { Text("PizzApp 🍕") }
            )
        },

        bottomBar = {
            NavigationBar {

                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("welcome") },
                    icon = { Icon(Icons.Default.Home, null) },
                    label = { Text("Accueil") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("list") },
                    icon = { Icon(Icons.AutoMirrored.Filled.List, null) },
                    label = { Text("Menu") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("caddy") },
                    icon = { Icon(Icons.Default.ShoppingCart, null) },
                    label = { Text("Panier") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("history") },
                    icon = { Icon(Icons.Default.CheckCircle, null) },
                    label = { Text("Historique") }
                )
            }
        }

    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = "welcome",
            modifier = Modifier.padding(innerPadding)
        ) {

            composable("welcome") {
                WelcomeScreen(navController)
            }

            composable("list") {
                PizzaMenu(
                    menu = pizzaViewModel.pizzas,
                    navController = navController
                )
            }

            composable("detail/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")?.toInt() ?: 0
                val pizza = pizzaViewModel.getPizzaById(id)

                DetailPizza(
                    pizza = pizza,
                    orderViewModel = orderViewModel,
                    navController = navController
                )
            }

            composable("caddy") {
                CaddyScreen(
                    orderViewModel = orderViewModel,
                    navController = navController
                )
            }

            composable("history") {
                HistoryScreen(orderViewModel)
            }
        }
    }
}