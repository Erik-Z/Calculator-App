package com.erikzhou.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.erikzhou.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CalculatorTheme {
                val viewModel = CalculatorViewModel()
                CalculatorUI(
                    viewModel = viewModel,
                )
            }
        }
    }
}