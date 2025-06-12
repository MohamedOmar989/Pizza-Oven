package com.example.pizzaoven.domin

data class Topping(
    val index: Int,
    val image: Int,
    val price: Int,
    val ingredients: List<Int>,
    val isSelected: Boolean = false
)