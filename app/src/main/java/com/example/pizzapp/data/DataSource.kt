package com.example.pizzapp.data

import com.example.pizzapp.model.Pizza
import com.example.pizzapp.R

class DataSource() {
    fun loadPizzas() : List<Pizza> {
        return listOf(
            Pizza(
                id = 0,
                name = "Margherita",
                price = 8.5,
                ingredients = listOf("Tomate", "Mozzarella", "Basilic"),
                image = R.drawable.pizza1
            ),
            Pizza(
                id = 1,
                name = "Pepperoni",
                price = 9.5,
                ingredients = listOf("Tomate", "Mozzarella", "Pepperoni"),
                image = R.drawable.pizza2
            ),
            Pizza(
                id = 2,
                name = "Quattro Formaggi",
                price = 10.0,
                ingredients = listOf("Tomate", "Mozzarella", "Gorgonzola", "Parmesan", "Chèvre"),
                image = R.drawable.pizza3
            ),
            Pizza(
                id = 3,
                name = "Hawaiian",
                price = 9.0,
                ingredients = listOf("Tomate", "Mozzarella", "Jambon", "Ananas"),
                image = R.drawable.pizza4
            ),
            Pizza(
                id = 4,
                name = "Vegetariana",
                price = 8.0,
                ingredients = listOf("Tomate", "Mozzarella", "Poivrons", "Oignons", "Champignons", "Aubergines"),
                image = R.drawable.pizza5
            ),
            Pizza(
                id = 5,
                name = "Diavola",
                price = 10.5,
                ingredients = listOf("Tomate", "Mozzarella", "Salami piquant", "Piment"),
                image = R.drawable.pizza6
            ),
            Pizza(
                id = 6,
                name = "BBQ Chicken",
                price = 11.0,
                ingredients = listOf("Sauce BBQ", "Mozzarella", "Poulet", "Oignons rouges"),
                image = R.drawable.pizza7
            ),
            Pizza(
                id = 7,
                name = "Prosciutto e Funghi",
                price = 13.5,
                ingredients = listOf("Tomate", "Mozzarella", "Jambon", "Champignons"),
                image = R.drawable.pizza8
            ),
            Pizza(
                id = 8,
                name = "Calzone",
                price = 9.5,
                ingredients = listOf("Tomate", "Mozzarella", "Jambon", "Oeuf", "Origan"),
                image = R.drawable.pizza9
            )
        )
    }
    fun loadPizza(id: Int) : Pizza {
        return loadPizzas()[id]
    }
}