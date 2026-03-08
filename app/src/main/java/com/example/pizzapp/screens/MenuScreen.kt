package com.example.pizzapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pizzapp.R
import com.example.pizzapp.model.Pizza
import com.example.pizzapp.viewmodel.OrderViewModel


@Composable
fun PizzaCard(
    pizza: Pizza,
    onClickPizza: () -> Unit,
    modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        onClick = onClickPizza,
    ) {
        Column(
            modifier = modifier
        ) {
            Text(
                text = pizza.name,
                modifier = modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.headlineMedium
            )
            Image(
                painter = painterResource(id = pizza.image),
                contentDescription = pizza.name,
                modifier = modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Prix = ${pizza.price} €",
                modifier = modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Composable
fun PizzaMenu(
    menu: List<Pizza>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {

        items(menu) { pizza ->

            PizzaCard(
                pizza = pizza,
                onClickPizza = {
                    navController.navigate("detail/${pizza.id}")
                },
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}


@Composable
fun DetailPizza(
    pizza: Pizza,
    orderViewModel: OrderViewModel,
    navController: NavController
) {

    var extraCheese by remember { mutableStateOf(50) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = pizza.image),
                contentDescription = pizza.name,
                modifier = Modifier.size(300.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = pizza.name,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Price: ${pizza.price} €")

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Extra Cheese: $extraCheese")

            Slider(
                value = extraCheese.toFloat(),
                onValueChange = { extraCheese = it.toInt() },
                valueRange = 0f..100f,
                steps = 4
            )
        }

        FloatingActionButton(
            onClick = {
                orderViewModel.addPizza(pizza, extraCheese)
                navController.navigate("caddy")
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.shopping_cart),
                contentDescription = "Commander"
            )
        }
    }
}

