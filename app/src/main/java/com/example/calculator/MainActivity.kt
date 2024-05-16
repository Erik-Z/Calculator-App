package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.example.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val result = remember {
                mutableStateOf("0")
            }
            CalculatorTheme {
                val viewModel = CalculatorViewModel()
                CalculatorUI(
                    viewModel = viewModel,
                )
            }
        }
    }
}