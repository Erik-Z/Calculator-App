package com.erikzhou.calculator.parser

import kotlin.math.cos
import kotlin.math.ln
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.tan


class PrattParser(private val tokens: List<Token>) {
    private var pos = 0

    private fun currentToken(): Token? = if (pos < tokens.size) tokens[pos] else null

    private fun nextToken(): Token? = tokens.getOrNull(pos++)

    private fun nud(token: Token): Expr {
        return when (token.type) {
            TokenType.NUMBER, TokenType.VARIABLE -> {
                Expr.Literal(token.value)
            }
            TokenType.LPAREN -> {
                val expr = parseExpression()
                nextToken() // Consume ')'
                expr
            }
            // There should not be a CONSTANT type due to preprocessConstants
            TokenType.FUNCTION -> {
                val functionName = token.value
                val argument = parseExpression()
                nextToken() // Consume ')'
                Expr.FunctionCall(functionName, argument)
            }
            else -> throw IllegalArgumentException("Unexpected token: $token")
        }
    }

    private fun led(token: Token, left: Expr): Expr {
        return when (token.type) {
            TokenType.OPERATOR -> {
                val right = parseExpression(lbp(token))
                Expr.Binary(left, token.value, right)
            }
            else -> throw IllegalArgumentException("Unexpected token: $token")
        }
    }

    private fun lbp(token: Token?): Int {
        return when (token?.value) {
            "+", "-" -> 10
            "*", "/" -> 20
            "^" -> 30
            else -> 0
        }
    }

    fun parseExpression(rbp: Int = 0): Expr {
        var token = nextToken() ?: throw IllegalArgumentException("Unexpected end of input")
        var left = nud(token)

        while (true) {
            val nextToken = currentToken()
            if (rbp >= lbp(nextToken)) break
            token = nextToken() ?: break
            left = led(token, left)
        }

        return left
    }
}

fun tokenize(input: String): List<Token> {
    val tokens = mutableListOf<Token>()
    var i = 0

    while (i < input.length) {
        when (val ch = input[i]) {
            in '0'..'9', '.', 'e', 'π', '%' -> {
                val start = i
                while (i < input.length && (input[i].isDigit() || input[i] in ".eπ%")) {
                    i++
                }
                tokens.add(Token(TokenType.CONSTANT, input.substring(start, i)))
            }
            in "+-*/^" -> {
                tokens.add(Token(TokenType.OPERATOR, ch.toString()))
                i++
            }
            '(' -> {
                tokens.add(Token(TokenType.LPAREN, ch.toString()))
                i++
            }
            ')' -> {
                tokens.add(Token(TokenType.RPAREN, ch.toString()))
                i++
            }
            in 'a'..'z' -> {
                // Tokenize function names
                val start = i
                while (i < input.length && input[i] in 'a'..'z') {
                    i++
                }
                tokens.add(Token(TokenType.FUNCTION, input.substring(start, i)))
            }
            else -> {
                i++
            }
        }
    }

    return tokens
}

sealed class Expr {
    data class Literal(val value: String) : Expr()
    data class Binary(val left: Expr, val operator: String, val right: Expr) : Expr()
    data class FunctionCall(val functionName: String, val argument: Expr) : Expr()
}

fun evaluateConstant(constant: String): Double {
    var value = 1.0
    var current = ""
    for (ch in constant) {
        when (ch) {
            'e' -> {
                value *= if (current.isEmpty()) Math.E else current.toDouble() * Math.E
                current = ""
            }
            'π' -> {
                value *= if (current.isEmpty()) Math.PI else current.toDouble() * Math.PI
                current = ""
            }
            '%' -> {
                value *= if (current.isEmpty()) 0.01 else current.toDouble() * 0.01
                current = ""
            }
            else -> current += ch
        }
    }
    if (current.isNotEmpty()) {
        value *= current.toDouble()
    }
    return value
}

fun handleImplicitMultiplication(tokens: List<Token>): List<Token> {
    val result = mutableListOf<Token>()

    for (i in tokens.indices) {
        result.add(tokens[i])
        if (i < tokens.size - 1) {
            val curr = tokens[i]
            val next = tokens[i + 1]
            if ((curr.type == TokenType.NUMBER || curr.type == TokenType.VARIABLE || curr.type == TokenType.RPAREN) &&
                (next.type == TokenType.NUMBER || next.type == TokenType.VARIABLE || next.type == TokenType.LPAREN || next.type == TokenType.FUNCTION)) {
                result.add(Token(TokenType.OPERATOR, "*"))
            }
        }
    }

    return result
}

fun preprocessConstants(tokens: List<Token>): List<Token> {
    val result = mutableListOf<Token>()
    var currentConstant = ""

    for (token in tokens) {
        if (token.type == TokenType.CONSTANT) {
            currentConstant += token.value
        } else {
            if (currentConstant.isNotEmpty()) {
                result.add(Token(TokenType.NUMBER, evaluateConstant(currentConstant).toString()))
                currentConstant = ""
            }
            result.add(token)
        }
    }

    if (currentConstant.isNotEmpty()) {
        result.add(Token(TokenType.NUMBER, evaluateConstant(currentConstant).toString()))
    }

    return result
}


//NULL, NUMBER, CONSTANT, OPERATOR, FUNCTION, LPAREN, RPAREN
fun handleNegativeSign(tokens: List<Token>): List<Token>{
    val result = mutableListOf<Token>()
    var previousToken = Token(TokenType.NULL, "")
    var isPreviousTokenNegative = false
    for (token in tokens) {
        if (token.type == TokenType.OPERATOR && token.value == "-") {
            // Negative sign
            if (previousToken.type == TokenType.NULL ||
                previousToken.type == TokenType.OPERATOR ||
                previousToken.type == TokenType.LPAREN
            ){
                // -1  negative NULL
                // (-8)  negative LPARAM
                // 5*-4  negative OPERATOR
                // (5)-5  subtraction RPARAM
                // 5-5 subtracton NUMBER
                // e-5 subtraction CONSTANT
                isPreviousTokenNegative = true
            } else {
                result.add(token)
            }
        } else if (token.type == TokenType.NUMBER || token.type == TokenType.CONSTANT){
            if (isPreviousTokenNegative){
                result.add(Token(TokenType.NUMBER, (token.value.toDouble() * -1).toString()))
                isPreviousTokenNegative = false
            } else {
                result.add(token)
            }
        } else {
            result.add(token)
        }
        previousToken = token

    }
    return result
}


fun evaluate(expr: Expr): Double {
    return when (expr) {
        is Expr.Literal -> expr.value.toDouble()
        is Expr.Binary -> {
            val leftValue = evaluate(expr.left)
            val rightValue = evaluate(expr.right)
            when (expr.operator) {
                "+" -> leftValue + rightValue
                "-" -> leftValue - rightValue
                "*" -> leftValue * rightValue
                "/" -> leftValue / rightValue
                "^" -> leftValue.pow(rightValue)
                else -> throw IllegalArgumentException("Unknown operator: ${expr.operator}")
            }
        }
        is Expr.FunctionCall -> {
            val argumentValue = evaluate(expr.argument)
            when (expr.functionName) {
                "ln" -> ln(argumentValue)
                "sin" -> sin(argumentValue)
                "cos" -> cos(argumentValue)
                "tan" -> tan(argumentValue)
                else -> throw IllegalArgumentException("Unknown function: ${expr.functionName}")
            }
        }
    }
}
