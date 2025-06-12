package com.example.pizzaoven.presentation.view_model


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.pizzaoven.R
import com.example.pizzaoven.domin.Pizza
import com.example.pizzaoven.domin.PizzaSize
import com.example.pizzaoven.domin.Topping
import com.example.pizzaoven.presentation.view_model.ui_state.PizzaScreenState
import kotlinx.coroutines.flow.update


class PizzaViewModel() : ViewModel() {

    private val _state = MutableStateFlow(PizzaScreenState())
    val state =  _state.asStateFlow()

    init {
        getPizza()
        getToppingList()
    }

    private fun getPizza() {
        _state.update {
            it.copy(pizzas = getPizzaList())
        }
    }

    private fun getToppingList() {
        _state.update {
            it.copy(topping = getTopping())
        }
    }

    fun onPizzaSelected(index: Int) {
        _state.update {
            it.copy(selectedPizzaIndex = index)
        }
    }

    fun onPizzaSizeChanged(newSize: PizzaSize) {
        val updatedPizzas = state.value.pizzas.toMutableList()
        val selectedIndex = state.value.selectedPizzaIndex
        val updatedPizza = updatedPizzas[selectedIndex].copy(size = newSize)
        updatedPizzas[selectedIndex] = updatedPizza
        _state.update { it.copy(pizzas = updatedPizzas) }
    }

    fun onToppingSelected(toppingIndex: Int) {
        val currentState = state.value
        val selectedPizzaIndex = currentState.selectedPizzaIndex
        val updatedPizzas = currentState.pizzas.toMutableList()
        val selectedPizza = updatedPizzas[selectedPizzaIndex]

        val updatedToppings = selectedPizza.topping.mapIndexed { index, topping ->
            if (index == toppingIndex) {
                topping.copy(isSelected = !topping.isSelected)
            } else {
                topping
            }
        }

        val updatedPizza = selectedPizza.copy(topping = updatedToppings)
        updatedPizzas[selectedPizzaIndex] = updatedPizza

        val updatedGlobalToppings = currentState.topping.mapIndexed { index, topping ->
            if (index == toppingIndex) {
                topping.copy(isSelected = !topping.isSelected)
            } else {
                topping
            }
        }

        _state.update {
            it.copy(
                pizzas = updatedPizzas,
                topping = updatedGlobalToppings
            )
        }
    }

    private fun getPizzaList(): List<Pizza> {
        return listOf(
            Pizza(R.drawable.bread_1, topping = getTopping(), price = 15),
            Pizza(R.drawable.bread_2, topping = getTopping(), price = 15),
            Pizza(R.drawable.bread_3, topping = getTopping(), price = 20),
            Pizza(R.drawable.bread_4, topping = getTopping(), price = 25),
            Pizza(R.drawable.bread_5, topping = getTopping(), price = 10),
        )
    }

    fun getTopping(): List<Topping> {
        return listOf(
            Topping(index = 0, image = R.drawable.basil_3, price = 5, ingredients = getListOfTopping("basil")),
            Topping(index = 1, image = R.drawable.broccoli_3, price = 5, ingredients = getListOfTopping("broccoli")),
            Topping(index = 2, image = R.drawable.onion_3, price = 5, ingredients = getListOfTopping("onion")),
            Topping(index = 3, image = R.drawable.mushroom_3, price = 5, ingredients = getListOfTopping("mushroom")),
            Topping(index = 4, image = R.drawable.sausage_3, price = 5, ingredients = getListOfTopping("sausage")),
        )
    }

    private fun getListOfTopping(imageName: String): List<Int> {
        return when (imageName) {
            "basil" -> listOf(
                R.drawable.basil_1,
                R.drawable.basil_2,
                R.drawable.basil_3,
                R.drawable.basil_4,
                R.drawable.basil_5,
                R.drawable.basil_6,
                R.drawable.basil_7,
                R.drawable.basil_8,
                R.drawable.basil_9,
                R.drawable.basil_10,
            )

            "broccoli" -> listOf(
                R.drawable.broccoli_1,
                R.drawable.broccoli_2,
                R.drawable.broccoli_3,
                R.drawable.broccoli_4,
                R.drawable.broccoli_5,
                R.drawable.broccoli_6,
                R.drawable.broccoli_7,
                R.drawable.broccoli_8,
                R.drawable.broccoli_9,
                R.drawable.broccoli_10,
            )

            "mushroom" -> listOf(
                R.drawable.mushroom_1,
                R.drawable.mushroom_2,
                R.drawable.mushroom_3,
                R.drawable.mushroom_4,
                R.drawable.mushroom_5,
                R.drawable.mushroom_6,
                R.drawable.mushroom_7,
                R.drawable.mushroom_8,
                R.drawable.mushroom_9,
                R.drawable.mushroom_10,
            )

            "onion" -> listOf(
                R.drawable.onion_1,
                R.drawable.onion_2,
                R.drawable.onion_3,
                R.drawable.onion_4,
                R.drawable.onion_5,
                R.drawable.onion_6,
                R.drawable.onion_7,
                R.drawable.onion_8,
                R.drawable.onion_9,
                R.drawable.onion_10,
            )

            "sausage" -> listOf(
                R.drawable.sausage_1,
                R.drawable.sausage_2,
                R.drawable.sausage_3,
                R.drawable.sausage_4,
                R.drawable.sausage_5,
                R.drawable.sausage_6,
                R.drawable.sausage_7,
                R.drawable.sausage_8,
                R.drawable.sausage_9,
                R.drawable.sausage_10,
            )
            else -> listOf()
        }
    }
}