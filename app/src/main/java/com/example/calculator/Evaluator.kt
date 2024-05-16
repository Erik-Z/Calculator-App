package com.example.calculator

import java.util.Stack
import kotlin.math.pow

public class Evaluator {
    companion object {
        private fun precedence (op: Char): Int {
            return when(op) {
                '+', '-' -> 1
                '*', '/' -> 2
                '^', -> 3
                else -> 0
            }
        }

        fun applyOp(a: Double, b: Double, op: Char): Double {
            return when(op) {
                '+' -> a + b
                '-' -> a - b
                '*' -> a * b
                '/' -> a / b
                '^' -> a.pow(b)
                else -> throw IllegalArgumentException("Invalid Operator")
            }
        }

        // TODO: Handle ePI
        fun infixToRPN(expression: String): List<String> {
            val stack = Stack<Char>()
            val output = mutableListOf<String>()
            var i = 0
            while (i < expression.length) {
                when {
                    expression[i].isDigit() -> {
                        val num = StringBuilder()
                        var numLength = 1
                        while(i < expression.length && (expression[i].isDigit() || expression[i] == '.')) {
                            num.append(expression[i])
                            i++
                            numLength++
                        }
                        // handle previous token is e or π
                        val isPreviousConstant = i > numLength - 1 && expression[i - numLength] in listOf('e', 'π')
                        if (isPreviousConstant) {
                            output.add(num.toString())
                            output.add("*")
                        } else {
                            output.add(num.toString())
                        }
                    }

                    expression[i] == '(' -> {
                        stack.push(expression[i])
                        i++
                    }

                    expression[i] == ')' -> {
                        while (stack.isNotEmpty() && stack.peek() != '(') {
                            output.add(stack.pop().toString())
                        }
                        if (stack.isNotEmpty() && stack.peek() == '(') {
                            stack.pop()
                        }
                        i++
                    }

                    expression[i] in listOf('+', '-', '*', '/', '^') -> {
                        while (stack.isNotEmpty() && precedence(stack.peek()) >= precedence(expression[i])) {
                            output.add(stack.pop().toString())
                        }
                        stack.push(expression[i])
                        i++
                    }

                    expression[i] in listOf('e', 'π') -> {
                        val isPreviousDigit = i > 0 && expression[i - 1].isDigit()
                        if (isPreviousDigit) {
                            output.add(expression[i].toString())
                            output.add("*")
                        } else {
                            output.add(expression[i].toString())
                        }
                        i++
                    }
                    else -> i++
                }
            }
            while (stack.isNotEmpty()) {
                output.add(stack.pop().toString())
            }
            return output
        }

        fun evaluateRPN(rpn: List<String>): Double{
            if (rpn.size == 1) {
                val token = rpn[0]
                return when {
                    token.isDigitsOrDot() -> token.toDouble()
                    token == "e" -> Math.E
                    token == "π" -> Math.PI
                    else -> throw IllegalArgumentException("Invalid single token: $token")
                }
            }

            val stack = Stack<Double>()
            for (token in rpn) {
                when {
                    token.isDigitsOrDot() -> stack.push(token.toDouble())
                    token == "e" -> stack.push(Math.E)
                    token == "π" -> stack.push(Math.PI)
                    else -> {
                        val b = stack.pop()
                        val a = stack.pop()
                        stack.push(applyOp(a, b, token[0]))
                    }
                }
            }

            return stack.pop()
        }

        private fun String.isDigitsOrDot(): Boolean {
            return all { it.isDigit() || it == '.' }
        }

        fun evaluateExpression(expression: String): Double {
            val rpn = infixToRPN(expression)
            return evaluateRPN(rpn)
        }
    }
}