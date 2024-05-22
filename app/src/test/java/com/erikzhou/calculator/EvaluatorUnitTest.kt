package com.erikzhou.calculator

import org.junit.Test

import org.junit.Assert.*

class EvaluatorUnitTest {
    @Test
    fun infixToRPN_isCorrect() {
        assertEquals(Evaluator.infixToRPN("(1+2)*(3/4)^(5+6)"), listOf("1", "2", "+", "3", "4", "/", "5", "6", "+", "^", "*"))
    }
    @Test
    fun evaluate_e_isCorrect() {
        assertEquals(Evaluator.evaluateExpression("e"), Math.E, 0.000001)
    }

    @Test
    fun evaluate_single_e_isCorrect() {
        assertEquals(Evaluator.evaluateExpression("e + 1"), Math.E + 1, 0.000001)
        assertEquals(Evaluator.evaluateExpression("e + e"), Math.E + Math.E, 0.000001)
        assertEquals(Evaluator.evaluateExpression("1 + e"), Math.E + 1, 0.000001)
        assertEquals(Evaluator.evaluateExpression("e * e"), Math.E * Math.E, 0.000001)
        assertEquals(Evaluator.evaluateExpression("e - e"), Math.E - Math.E, 0.000001)
        assertEquals(Evaluator.evaluateExpression("e / e"), Math.E / Math.E, 0.000001)
    }

    @Test
    fun evaluate_e_next_to_digit_isCorrect() {
        assertEquals(Evaluator.evaluateExpression("9e"), Math.E * 9, 0.000001)
        assertEquals(Evaluator.evaluateExpression("e9"), Math.E * 9, 0.000001)
        assertEquals(Evaluator.evaluateExpression("9e9"), 9 * Math.E * 9, 0.000001)
        assertEquals(Evaluator.evaluateExpression("9e + 1"), Math.E * 9 + 1, 0.000001)
        assertEquals(Evaluator.evaluateExpression("e9 + 1"), Math.E * 9 + 1, 0.000001)
        assertEquals(Evaluator.evaluateExpression("9e9 + 1"), 9 * Math.E * 9 + 1, 0.000001)
        assertEquals(Evaluator.evaluateExpression("101 + 9e9"), 9 * Math.E * 9 + 101, 0.000001)
        assertEquals(Evaluator.evaluateExpression("e29"), Math.E * 29, 0.000001)
        assertEquals(Evaluator.evaluateExpression("29e"), Math.E * 29, 0.000001)
    }
    @Test
    fun evaluate_single_pi_isCorrect() {
        assertEquals(Evaluator.evaluateExpression("π + 1"), Math.PI + 1, 0.000001)
        assertEquals(Evaluator.evaluateExpression("π + π"), Math.PI + Math.PI, 0.000001)
        assertEquals(Evaluator.evaluateExpression("1 + π"), Math.PI + 1, 0.000001)
        assertEquals(Evaluator.evaluateExpression("π * π"), Math.PI * Math.PI, 0.000001)
        assertEquals(Evaluator.evaluateExpression("π - π"), Math.PI - Math.PI, 0.000001)
        assertEquals(Evaluator.evaluateExpression("π / π"), Math.PI / Math.PI, 0.000001)
    }

    @Test
    fun evaluate_pi_next_to_digit_isCorrect() {
        assertEquals(Evaluator.evaluateExpression("9π"), Math.PI * 9, 0.000001)
        assertEquals(Evaluator.evaluateExpression("π9"), Math.PI * 9, 0.000001)
        assertEquals(Evaluator.evaluateExpression("9π9"), 9 * Math.PI * 9, 0.000001)
        assertEquals(Evaluator.evaluateExpression("9π + 1"), Math.PI * 9 + 1, 0.000001)
        assertEquals(Evaluator.evaluateExpression("π9 + 1"), Math.PI * 9 + 1, 0.000001)
        assertEquals(Evaluator.evaluateExpression("9π9 + 1"), 9 * Math.PI * 9 + 1, 0.000001)
        assertEquals(Evaluator.evaluateExpression("101 + 9π9"), 9 * Math.PI * 9 + 101, 0.000001)
        assertEquals(Evaluator.evaluateExpression("π29"), Math.PI * 29, 0.000001)
        assertEquals(Evaluator.evaluateExpression("29π"), Math.PI * 29, 0.000001)
    }

    @Test
    fun evaluate_constant_next_to_constant_isCorrect() {
        assertEquals(Evaluator.evaluateExpression("ππ"), Math.PI * Math.PI, 0.000001)
        assertEquals(Evaluator.evaluateExpression("ee"), Math.E * Math.E, 0.000001)
        assertEquals(Evaluator.evaluateExpression("eπ"), Math.E * Math.PI, 0.000001)
        assertEquals(Evaluator.evaluateExpression("πe"), Math.E * Math.PI, 0.000001)
    }

    @Test
    fun evaluate_percent_sign_isCorrect() {
        assertEquals(Evaluator.evaluateExpression("9%"), 9 * 0.01, 0.000001)
        assertEquals(Evaluator.evaluateExpression("%9"), 9 * 0.01, 0.000001)
        assertEquals(Evaluator.evaluateExpression("9%9"), 9 * 0.01 * 9, 0.000001)
    }
}