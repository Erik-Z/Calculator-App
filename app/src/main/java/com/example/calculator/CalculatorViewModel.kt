package com.example.calculator

import androidx.compose.runtime.mutableStateOf
import kotlin.math.pow

class CalculatorViewModel {
    val expression = mutableStateOf("")
    fun clear() {
        expression.value = ""
    }

    fun delete() {
        expression.value = expression.value.dropLast(1)
    }

    private fun roundToDecimalPlaces(value: Double, decimalPlaces: Int): Double {
        val factor = 10.0.pow(decimalPlaces)
        return Math.round(value * factor) / factor
    }
    fun evaluate() {
        expression.value = roundToDecimalPlaces(Evaluator.evaluateExpression(expression.value), 5).toString()
    }

    fun append(char: String){
        if (char in "0123456789eπ") {
            expression.value += char
        }
        else if (char in "-") {
            if (expression.value.isNotEmpty()) {
                val lastChar = expression.value.last()

                if (lastChar in "-") {
                    expression.value = expression.value.dropLast(1)
                }
            }
        }
        else if(char in "+*/") {
            if (expression.value.isNotEmpty()) {
                val lastChar = expression.value.last()

                if (lastChar in "+-*/") {
                    expression.value = expression.value.dropLast(1)
                }
            }
            expression.value += char
        }
        else if(char == ".") {
            if (expression.value.isNotEmpty()) {
                val lastChar = expression.value.last()
                if (lastChar != '.') {
                    if (lastChar in "+-×÷") {
                        expression.value += "0"
                    }
                    expression.value += char
                }
            } else {
                expression.value += "0."
            }
        }
    }
}