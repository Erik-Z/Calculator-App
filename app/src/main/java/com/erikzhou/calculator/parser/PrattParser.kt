package com.erikzhou.calculator.parser


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
            in '0'..'9', 'e', 'π', '%' -> {
                val start = i
                while (i < input.length && (input[i].isDigit() || input[i] in "eπ%")) {
                    i++
                }
                tokens.add(Token(TokenType.NUMBER, input.substring(start, i)))
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
}

fun handleImplicitMultiplication(tokens: List<Token>): List<Token> {
    val result = mutableListOf<Token>()

    for (i in tokens.indices) {
        result.add(tokens[i])
        if (i < tokens.size - 1) {
            val curr = tokens[i]
            val next = tokens[i + 1]
            if ((curr.type == TokenType.NUMBER || curr.type == TokenType.VARIABLE || curr.type == TokenType.RPAREN) &&
                (next.type == TokenType.NUMBER || next.type == TokenType.VARIABLE || next.type == TokenType.LPAREN)) {
                result.add(Token(TokenType.OPERATOR, "*"))
            }
        }
    }

    return result
}

fun preprocessConstants(tokens: List<Token>): List<Token> {
    return tokens.map { token ->
        if (token.type == TokenType.NUMBER) {
            val value = when {
                token.value.contains("e") -> token.value.replace("e", "").toDouble() * Math.E
                token.value.contains("π") -> token.value.replace("π", "").toDouble() * Math.PI
                token.value.contains("%") -> token.value.replace("%", "").toDouble() * 0.01
                else -> token.value.toDouble()
            }
            Token(TokenType.NUMBER, value.toString())
        } else {
            token
        }
    }
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
                "^" -> Math.pow(leftValue, rightValue)
                else -> throw IllegalArgumentException("Unknown operator: ${expr.operator}")
            }
        }
    }
}
