package com.example.calculator

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import kotlin.math.pow

class CalculatorViewModel {
    val expression = mutableStateOf("")
    var numOfLeftParentheses = mutableIntStateOf(0)
    fun clear() {
        expression.value = ""
    }

    fun delete() {
        if (expression.value.last() == ')'){
           numOfLeftParentheses.intValue += 1
        } else if (expression.value.last() == '(') {
            numOfLeftParentheses.intValue -= 1
        }
        expression.value = expression.value.dropLast(1)
    }

    private fun roundToDecimalPlaces(value: Double, decimalPlaces: Int): Double {
        val factor = 10.0.pow(decimalPlaces)
        return Math.round(value * factor) / factor
    }
    fun evaluate() {
        try {
            expression.value =
                roundToDecimalPlaces(Evaluator.evaluateExpression(expression.value), 5).toString()
            numOfLeftParentheses.intValue = 0
        } catch (e: Exception) {
            expression.value = "INVALID"
        }
    }

    fun append(char: String){
        if (char in "0123456789eπ()") {
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

    fun appendParentheses() {
        if (expression.value.isNotEmpty()) {
            if (expression.value.last() == '(') {
                numOfLeftParentheses.intValue += 1
                append("(")
            } else if (expression.value.last() == ')' && numOfLeftParentheses.intValue > 0) {
                numOfLeftParentheses.intValue -= 1
                append(")")
            } else {
                if (numOfLeftParentheses.intValue > 0) {
                    numOfLeftParentheses.intValue -= 1
                    append(")")
                } else {
                    numOfLeftParentheses.intValue += 1
                    append("(")
                }
            }
        } else {
            numOfLeftParentheses.intValue += 1
            append("(")
        }
    }
}