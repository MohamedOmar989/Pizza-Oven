package com.example.pizzaoven.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pizzaoven.R
import com.example.pizzaoven.domin.Pizza
import com.example.pizzaoven.domin.Topping
import kotlin.random.Random

const val NUM_OF_INGREDIENTS = 3

@Composable
fun PizzaImage(
    modifier: Modifier = Modifier,
    pizzas: List<Pizza>,
    selectedPizzaIndex: Int,
    onPizzaPageChanged: (Int) -> Unit
){
    val pagerState = rememberPagerState(
        initialPage = selectedPizzaIndex,
        pageCount = {pizzas.size}
    )
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            onPizzaPageChanged(page)
        }
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.plate),
            contentDescription = "Plate image",
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .align(Alignment.Center)
        )
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
        ) { page ->
            val pizza = pizzas[page]
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = pizza.image),
                    contentDescription = "Pizza ${page + 1}",
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .scale(pizza.size.scale)
                )

                pizza.topping.forEach { topping ->
                    AnimatedIngredientsBox(
                        topping = topping,
                        pizzaScale = pizza.size.scale
                    )
                }
            }
        }
    }
}

@Composable
fun AnimatedIngredientsBox(
    topping: Topping,
    pizzaScale: Float,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = topping.isSelected,
        enter = scaleIn(initialScale = 40f, animationSpec = tween(500)),
        exit = fadeOut(tween(200)),
    ) {
        Box(
            modifier = modifier
                .padding(
                    start = 24.dp,
                    top = 24.dp
                )
                .clip(CircleShape)
                .size(200.dp),
            contentAlignment = Alignment.Center
        ) {
            repeat(NUM_OF_INGREDIENTS) {
                topping.ingredients.forEach { ingredientImage ->
                    val x = remember { (Random.nextFloat() - 0.6f) * 300 }
                    val y = remember { (Random.nextFloat() - 0.6f) * 300 }
                    val animatedX by animateFloatAsState(
                        targetValue = x * pizzaScale * 0.9f,
                        animationSpec = tween(100),
                        label = "ingredientX"
                    )
                    val animatedY by animateFloatAsState(
                        targetValue = y * pizzaScale * 0.9f,
                        animationSpec = tween(100),
                        label = "ingredientY"
                    )
                    Image(
                        painter = painterResource(id = ingredientImage),
                        contentDescription = null,
                        modifier = Modifier
                            .size((44 * pizzaScale).dp)
                            .offset(
                                x = animatedX.dp,
                                y = animatedY.dp,
                            ),
                        contentScale = ContentScale.Fit,
                    )
                }
            }
        }
    }
}