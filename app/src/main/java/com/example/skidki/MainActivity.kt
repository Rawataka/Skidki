package com.example.skidki

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skidki.ui.theme.SkidkiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SkidkiTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DemoScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun DemoTextPreview() {
    SkidkiTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            DemoScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun DemoSlider(sliderPosition: Float, onPositionChange: (Float) -> Unit) {
    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        Text(
            text = "Чаевые:",
            fontSize = 22.sp,
            modifier = Modifier.align(Alignment.Start)
        )
        Slider(
            valueRange = 0f..25f,
            value = sliderPosition,
            onValueChange = { onPositionChange(it) }
        )
    }
}

@Composable
fun DemoScreen(modifier: Modifier = Modifier) {
    var order by remember { mutableStateOf("") }
    var dishCount by remember { mutableStateOf("") }
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    var sale by remember { mutableIntStateOf(0) }

    // Функция для расчета скидки на основе количества блюд
    val calculateAutoSale = {
        val count = dishCount.toIntOrNull() ?: 0
        sale = when {
            count >= 10 -> 10
            count >= 6 -> 7
            count >= 3 -> 5
            count >= 1 -> 3
            else -> 0
        }
    }

    val handlePositionChange = { position: Float ->
        sliderPosition = position
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "Калькулятор скидок и чаевых",
            fontSize = 24.sp,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Сумма заказа
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text(
                text = "Сумма заказа:",
                fontSize = 22.sp,
                modifier = Modifier.width(150.dp)
            )
            TextField(
                value = order,
                onValueChange = {
                    order = it
                    calculateAutoSale()
                },
                modifier = Modifier.width(190.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Количество блюд
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text(
                text = "Количество блюд:",
                fontSize = 22.sp,
                modifier = Modifier.width(150.dp)
            )
            TextField(
                value = dishCount,
                onValueChange = {
                    dishCount = it
                    calculateAutoSale()
                },
                modifier = Modifier.width(150.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Слайдер для чаевых
        DemoSlider(
            sliderPosition = sliderPosition,
            onPositionChange = handlePositionChange
        )

        Text(
            style = MaterialTheme.typography.headlineMedium,
            text = "${sliderPosition.toInt()}%"
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Отображение текущей скидки
        Text(
            text = "Текущая скидка: $sale%",
            fontSize = 18.sp
        )
    }
}