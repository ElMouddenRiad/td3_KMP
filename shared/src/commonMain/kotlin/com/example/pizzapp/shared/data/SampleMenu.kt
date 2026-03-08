package com.example.pizzapp.shared.data

import com.example.pizzapp.shared.model.Pizza

object SampleMenu {

    fun pizzas(): List<Pizza> {
        return listOf(
            Pizza(
                id = 0,
                name = "Margherita",
                price = 8.5,
                ingredients = listOf("Tomate", "Mozzarella", "Basilic"),
                image = 1
            ),
            Pizza(
                id = 1,
                name = "Pepperoni",
                price = 9.5,
                ingredients = listOf("Tomate", "Mozzarella", "Pepperoni"),
                image = 2
            ),
            Pizza(
                id = 2,
                name = "Quattro Formaggi",
                price = 10.0,
                ingredients = listOf("Tomate", "Mozzarella", "Gorgonzola", "Parmesan", "Chèvre"),
                image = 3
            ),
            Pizza(
                id = 3,
                name = "Hawaiian",
                price = 9.0,
                ingredients = listOf("Tomate", "Mozzarella", "Jambon", "Ananas"),
                image = 4
            ),
            Pizza(
                id = 4,
                name = "Vegetariana",
                price = 8.0,
                ingredients = listOf("Tomate", "Mozzarella", "Poivrons", "Oignons", "Champignons", "Aubergines"),
                image = 5
            ),
            Pizza(
                id = 5,
                name = "Diavola",
                price = 10.5,
                ingredients = listOf("Tomate", "Mozzarella", "Salami piquant", "Piment"),
                image = 6
            ),
            Pizza(
                id = 6,
                name = "BBQ Chicken",
                price = 11.0,
                ingredients = listOf("Sauce BBQ", "Mozzarella", "Poulet", "Oignons rouges"),
                image = 7
            ),
            Pizza(
                id = 7,
                name = "Prosciutto e Funghi",
                price = 13.5,
                ingredients = listOf("Tomate", "Mozzarella", "Jambon", "Champignons"),
                image = 8
            ),
            Pizza(
                id = 8,
                name = "Calzone",
                price = 9.5,
                ingredients = listOf("Tomate", "Mozzarella", "Jambon", "Oeuf", "Origan"),
                image = 9
            )
        )
    }
}
