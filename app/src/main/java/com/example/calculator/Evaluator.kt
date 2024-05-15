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

        fun infixToRPN(expression: String): List<String> {
            val stack = Stack<Char>()
            val output = mutableListOf<String>()
            var i = 0
            while (i < expression.length) {
                when {
                    expression[i].isDigit() -> {
                        val num = StringBuilder()
                        while(i < expression.length && (expression[i].isDigit() || expression[i] == '.')) {
                            num.append(expression[i])
                            i++
                        }
                        output.add(num.toString())
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
                    else -> i++
                }
            }
            while (stack.isNotEmpty()) {
                output.add(stack.pop().toString())
            }
            return output
        }
    }
}