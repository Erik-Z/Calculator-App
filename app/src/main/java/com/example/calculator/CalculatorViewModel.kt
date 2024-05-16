package com.example.calculator

import androidx.compose.runtime.mutableStateOf
import kotlin.math.exp

class CalculatorViewModel {
    val expression = mutableStateOf("")
    fun clear() {
        expression.value = ""
    }

    fun delete() {
        expression.value = expression.value.dropLast(1)
    }

    fun evaluate() {
        expression.value = Evaluator.evaluateExpression(expression.value).toString()
    }

    fun append(char: String){
        if (char in "0123456789") {
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