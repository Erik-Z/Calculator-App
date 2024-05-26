package com.erikzhou.calculator

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.substring
import com.erikzhou.calculator.parser.PrattParser
import com.erikzhou.calculator.parser.handleImplicitMultiplication
import com.erikzhou.calculator.parser.preprocessConstants
import com.erikzhou.calculator.parser.tokenize
import kotlin.math.pow

class CalculatorViewModel {
    val expression = mutableStateOf("")
    var numOfLeftParentheses = mutableIntStateOf(0)
    fun clear() {
        expression.value = ""
        numOfLeftParentheses.intValue = 0
    }

    fun delete() {
        if (expression.value.isEmpty()) return
        if (expression.value.last() == ')') {
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

    private fun formatRoundedValue(value: Double, decimalPlaces: Int): String {
        val roundedValue = roundToDecimalPlaces(value, decimalPlaces)
        return if (roundedValue % 1.0 == 0.0) {
            roundedValue.toInt().toString() // Convert to Int and then to String if there's no fractional part
        } else {
            roundedValue.toString() // Otherwise, return the double as a string
        }
    }

    fun evaluateExpression(expression: String): Double{
        val tokens = preprocessConstants(handleImplicitMultiplication(tokenize(expression)))
        val parser = PrattParser(tokens)
        val parsedExpression = parser.parseExpression()
        return com.erikzhou.calculator.parser.evaluate(parsedExpression)
    }

    fun evaluate() {
        try {
            val formattedValue = formatRoundedValue(evaluateExpression(expression.value), 5)
            expression.value = formattedValue
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
            expression.value += char
        }
        else if(char in "+*/") {
            if (expression.value.length > 1){
                val secondToLastChar = expression.value.substring(0..expression.value.length - 2).last()
                val lastChar = expression.value.last()
                if (lastChar in "-" && secondToLastChar in "+*/") {
                    expression.value = expression.value.dropLast(1)
                    return
                } else if (lastChar in "+*/-"){
                    expression.value = expression.value.dropLast(1)
                }
            } else if (expression.value.isNotEmpty()) {
                val lastChar = expression.value.last()
                if (lastChar in "-(") return

            } else {
                return
            }
            expression.value += char
        }
        else if(char == "%"){
            if (expression.value.isEmpty()) return

            val lastChar = expression.value.last()
            if (lastChar in "+-*/") {
                return
            } else {
                expression.value += "%"
            }
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