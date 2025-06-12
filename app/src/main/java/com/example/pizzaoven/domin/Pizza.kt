package com.example.pizzaoven.domin

data class Pizza(
    val image: Int,
    val size: PizzaSize = PizzaSize.M,
    val topping: List<Topping>,
    val price: Int
) {
    val totalPrice: Int
        get() = price + topping.filter { it.isSelected }.sumOf { it.price } + size.price
}