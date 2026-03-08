package com.example.pizzapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pizzapp.viewmodel.OrderViewModel

@Composable
fun CaddyScreen(
    orderViewModel: OrderViewModel,
    navController: NavController
) {

    val order = orderViewModel.order

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        if (order.isEmpty()) {

            Text("Votre panier est vide")

        } else {

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {

                items(order) { item ->

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Column {
                            Text(item.pizza.name)
                            Text("Extra cheese: ${item.extraCheese}")
                            Text("Quantité: ${item.quantity}")
                        }

                        Row {

                            Button(
                                onClick = {
                                    orderViewModel.decreaseQuantity(item)
                                }
                            ) {
                                Text("-")
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Button(
                                onClick = {
                                    orderViewModel.increaseQuantity(item)
                                }
                            ) {
                                Text("+")
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Total : ${orderViewModel.getTotalPrice()} €",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    orderViewModel.clearOrder()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Annuler la commande")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    orderViewModel.saveOrder()
                    orderViewModel.clearOrder()
                    navController.navigate("list")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Passer la commande")
            }
        }
    }
}