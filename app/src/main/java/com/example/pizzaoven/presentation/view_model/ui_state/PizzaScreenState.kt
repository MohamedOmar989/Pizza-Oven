package com.example.pizzaoven.presentation.view_model.ui_state

import com.example.pizzaoven.domin.Pizza
import com.example.pizzaoven.domin.Topping

data class PizzaScreenState(
    val pizzas: List<Pizza> = emptyList(),
    val selectedPizzaIndex: Int = 0,
    val topping: List<Topping> = emptyList(),
    val totalPrice: Int = 0
)