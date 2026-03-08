package com.example.pizzapp.shared.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pizzapp.shared.data.SampleMenu
import com.example.pizzapp.shared.domain.OrderService
import com.example.pizzapp.shared.model.Pizza
import kotlinx.coroutines.launch
import kotlin.math.round
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import pizzapp.shared.generated.resources.Res
import pizzapp.shared.generated.resources.logo
import pizzapp.shared.generated.resources.pizza1
import pizzapp.shared.generated.resources.pizza2
import pizzapp.shared.generated.resources.pizza3
import pizzapp.shared.generated.resources.pizza4
import pizzapp.shared.generated.resources.pizza5
import pizzapp.shared.generated.resources.pizza6
import pizzapp.shared.generated.resources.pizza7
import pizzapp.shared.generated.resources.pizza8
import pizzapp.shared.generated.resources.pizza9

private enum class SharedScreen {
    WELCOME,
    MENU,
    DETAIL,
    CADDY,
    HISTORY
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PizzAppSharedScreen(
    orderService: OrderService,
    title: String,
    nowMillisProvider: () -> Long = { 0L }
) {
    val pizzas = remember { SampleMenu.pizzas() }
    val scope = rememberCoroutineScope()
    val history by orderService.orderHistory.collectAsState(initial = emptyList())
    var renderTick by remember { mutableIntStateOf(0) }
    var currentScreen by remember { mutableStateOf(SharedScreen.WELCOME) }
    var selectedPizza by remember { mutableStateOf<Pizza?>(null) }
    var detailExtraCheese by remember { mutableIntStateOf(50) }

    val cartItems = remember(renderTick, history) { orderService.cartItems() }
    val total = remember(renderTick, history) { round(orderService.totalPrice() * 100) / 100 }

    LaunchedEffect(renderTick) {
        // Keep detail screen in sync if selected pizza disappears from menu changes.
        if (currentScreen == SharedScreen.DETAIL && selectedPizza == null) {
            currentScreen = SharedScreen.MENU
        }
    }

    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(title) })
            },
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentScreen == SharedScreen.WELCOME,
                        onClick = { currentScreen = SharedScreen.WELCOME },
                        icon = { Icon(Icons.Default.Home, contentDescription = null) },
                        label = { Text("Accueil") }
                    )
                    NavigationBarItem(
                        selected = currentScreen == SharedScreen.MENU,
                        onClick = { currentScreen = SharedScreen.MENU },
                        icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = null) },
                        label = { Text("Menu") }
                    )
                    NavigationBarItem(
                        selected = currentScreen == SharedScreen.CADDY,
                        onClick = { currentScreen = SharedScreen.CADDY },
                        icon = { Icon(Icons.Default.ShoppingCart, contentDescription = null) },
                        label = { Text("Panier") }
                    )
                    NavigationBarItem(
                        selected = currentScreen == SharedScreen.HISTORY,
                        onClick = { currentScreen = SharedScreen.HISTORY },
                        icon = { Icon(Icons.Default.CheckCircle, contentDescription = null) },
                        label = { Text("Historique") }
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (currentScreen) {
                    SharedScreen.WELCOME -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.logo),
                                contentDescription = "Logo PizzApp",
                                modifier = Modifier.size(160.dp)
                            )
                            Spacer(Modifier.height(16.dp))
                            Text("Bienvenue dans PizzApp", style = MaterialTheme.typography.headlineMedium)
                            Spacer(Modifier.height(16.dp))
                            Button(onClick = { currentScreen = SharedScreen.MENU }) {
                                Text("Voir le menu")
                            }
                        }
                    }

                    SharedScreen.MENU -> {
                        LazyColumn(modifier = Modifier.padding(16.dp)) {
                            items(pizzas) { pizza ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    onClick = {
                                        selectedPizza = pizza
                                        detailExtraCheese = 50
                                        currentScreen = SharedScreen.DETAIL
                                    }
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Text(pizza.name, style = MaterialTheme.typography.headlineSmall)
                                        Spacer(Modifier.height(8.dp))
                                        Image(
                                            painter = painterResource(pizzaDrawable(pizza.image)),
                                            contentDescription = pizza.name,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(160.dp)
                                        )
                                        Spacer(Modifier.height(8.dp))
                                        Text("Prix : ${pizza.price} €")
                                    }
                                }
                            }
                        }
                    }

                    SharedScreen.DETAIL -> {
                        val pizza = selectedPizza
                        if (pizza == null) {
                            currentScreen = SharedScreen.MENU
                        } else {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(pizzaDrawable(pizza.image)),
                                    contentDescription = pizza.name,
                                    modifier = Modifier.size(220.dp)
                                )
                                Spacer(Modifier.height(16.dp))
                                Text(pizza.name, style = MaterialTheme.typography.headlineMedium)
                                Spacer(Modifier.height(12.dp))
                                Text("Prix : ${pizza.price} €")
                                Spacer(Modifier.height(12.dp))
                                Text("Ingrédients : ${pizza.ingredients.joinToString()}")
                                Spacer(Modifier.height(16.dp))
                                Text("Extra fromage : $detailExtraCheese")
                                Slider(
                                    value = detailExtraCheese.toFloat(),
                                    onValueChange = { detailExtraCheese = it.toInt() },
                                    valueRange = 0f..100f,
                                    steps = 4
                                )
                            }

                            FloatingActionButton(
                                onClick = {
                                    orderService.addPizza(pizza, detailExtraCheese)
                                    renderTick++
                                    currentScreen = SharedScreen.CADDY
                                },
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(16.dp)
                            ) {
                                Text("+")
                            }
                        }
                    }

                    SharedScreen.CADDY -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            if (cartItems.isEmpty()) {
                                Text("Votre panier est vide")
                            } else {
                                LazyColumn(modifier = Modifier.weight(1f)) {
                                    items(cartItems) { item ->
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Column {
                                                Text(item.pizza.name)
                                                Text("Extra fromage : ${item.extraCheese}")
                                                Text("Quantité : ${item.quantity}")
                                            }

                                            Row {
                                                Button(onClick = {
                                                    orderService.decreaseQuantity(item.pizza.id, item.extraCheese)
                                                    renderTick++
                                                }) {
                                                    Text("-")
                                                }
                                                Spacer(Modifier.width(8.dp))
                                                Button(onClick = {
                                                    orderService.increaseQuantity(item.pizza.id, item.extraCheese)
                                                    renderTick++
                                                }) {
                                                    Text("+")
                                                }
                                            }
                                        }
                                        Spacer(Modifier.height(12.dp))
                                    }
                                }

                                Text("Total : $total €", style = MaterialTheme.typography.headlineSmall)
                                Spacer(Modifier.height(12.dp))

                                Button(
                                    onClick = {
                                        orderService.clearCart()
                                        renderTick++
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Annuler la commande")
                                }

                                Spacer(Modifier.height(8.dp))

                                Button(
                                    onClick = {
                                        scope.launch {
                                            orderService.checkout(nowMillisProvider())
                                            renderTick++
                                            currentScreen = SharedScreen.HISTORY
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Passer la commande")
                                }
                            }
                        }
                    }

                    SharedScreen.HISTORY -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            Text("Historique des commandes", style = MaterialTheme.typography.headlineMedium)
                            Spacer(Modifier.height(16.dp))

                            LazyColumn {
                                items(history) { order ->
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp)
                                    ) {
                                        Column(modifier = Modifier.padding(16.dp)) {
                                            Text("Commande #${order.id}")
                                            Text("Total : ${order.totalPrice} €")
                                            order.items.forEach { item ->
                                                Text("• ${item.pizzaName} x${item.quantity} (extra fromage : ${item.extraCheese})")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
private fun pizzaDrawable(imageId: Int) = when (imageId) {
    1 -> Res.drawable.pizza1
    2 -> Res.drawable.pizza2
    3 -> Res.drawable.pizza3
    4 -> Res.drawable.pizza4
    5 -> Res.drawable.pizza5
    6 -> Res.drawable.pizza6
    7 -> Res.drawable.pizza7
    8 -> Res.drawable.pizza8
    else -> Res.drawable.pizza9
}
