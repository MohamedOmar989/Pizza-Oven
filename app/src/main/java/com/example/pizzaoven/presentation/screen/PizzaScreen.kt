package com.example.pizzaoven.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pizzaoven.domin.PizzaSize
import com.example.pizzaoven.presentation.components.ToppingsRow
import com.example.pizzaoven.presentation.components.PizzaButton
import com.example.pizzaoven.presentation.components.PizzaImage
import com.example.pizzaoven.presentation.components.SizeRow
import com.example.pizzaoven.presentation.components.TopBar
import com.example.pizzaoven.presentation.view_model.ui_state.PizzaScreenState
import com.example.pizzaoven.presentation.view_model.PizzaViewModel

@Composable
fun PizzaScreen(
    viewModel: PizzaViewModel
) {
    val state by viewModel.state.collectAsState()
    PizzaScreenContent(
        state = state,
        onPizzaSelected = viewModel::onPizzaSelected,
        onPizzaSizeChanged = viewModel::onPizzaSizeChanged
    )
}

@Composable
fun PizzaScreenContent(
    state : PizzaScreenState,
    onPizzaSelected: (Int) -> Unit,
    onPizzaSizeChanged: (PizzaSize) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(25.dp))
        PizzaImage(
            pizzas = state.pizzas,
            selectedPizzaIndex = state.selectedPizzaIndex,
            onPizzaPageChanged = onPizzaSelected
        )
        Spacer(Modifier.height(32.dp))
        Text(
            text = "$${state.pizzas[state.selectedPizzaIndex].totalPrice}",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(25.dp))
        SizeRow(
            selectedSize = state.pizzas[state.selectedPizzaIndex].size,
            onItemClick = onPizzaSizeChanged
        )
        Spacer(Modifier.weight(.4f))
        Text(
            text = "CUSTOMIZE YOUR PIZZA",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            color = Color.Gray.copy(alpha = .5f)
        )
        Spacer(Modifier.weight(0.6f))
        ToppingsRow(
            ingredients = state.topping,
            onIngredientClick = {}
        )
        Spacer(modifier = Modifier.weight(1f))
        PizzaButton(
            modifier = Modifier
                .padding(bottom = 40.dp)
        )
    }
}
