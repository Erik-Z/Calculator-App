package com.example.calculator

import org.junit.Test

import org.junit.Assert.*

class EvaluatorUnitTest {
    @Test
    fun infixToRPN_isCorrect() {
        assertEquals(Evaluator.infixToRPN("(1+2)*(3/4)^(5+6)"), listOf("1", "2", "+", "3", "4", "/", "5", "6", "+", "^", "*"))
    }
}