package com.example.pizzapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pizzapp.viewmodel.OrderViewModel
import java.util.Date

@Composable
fun HistoryScreen(orderViewModel: OrderViewModel) {

    val orders by orderViewModel.orderHistory.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Historique des commandes",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {

            items(orders) { order ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Commande #${order.order.id}")
                        Text("Total : ${order.order.totalPrice} €")
                        Text("Date : ${Date(order.order.date)}")

                        Spacer(modifier = Modifier.height(8.dp))

                        order.items.forEach { item ->
                            Text(
                                text = "• ${item.pizzaName} x${item.quantity} (extra cheese: ${item.extraCheese})"
                            )
                        }
                    }
                }
            }
        }
    }
}