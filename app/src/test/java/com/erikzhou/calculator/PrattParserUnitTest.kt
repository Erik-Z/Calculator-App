package com.erikzhou.calculator

import com.erikzhou.calculator.parser.PrattParser
import com.erikzhou.calculator.parser.evaluate
import com.erikzhou.calculator.parser.handleImplicitMultiplication
import com.erikzhou.calculator.parser.preprocessConstants
import com.erikzhou.calculator.parser.tokenize
import org.junit.Test

import org.junit.Assert.*
import javax.xml.xpath.XPathExpression
import kotlin.math.pow

class PrattParserUnitTest {

    fun evaluateExpression(expression: String): Double{
        val tokens = preprocessConstants(handleImplicitMultiplication(tokenize(expression)))
        val parser = PrattParser(tokens)
        val parsedExpression = parser.parseExpression()
        return evaluate(parsedExpression)
    }

    @Test
    fun PrattParser_isCorrect() {
        assertEquals(evaluateExpression("6.7 + 6"), 12.7, 0.000001)
        assertEquals(evaluateExpression("((1+2)*4)^(5+6)"), ((1 + 2) * 4).toDouble().pow(11.toDouble()), 0.000001)
        assertEquals(evaluateExpression("(5 + 6)(11 - 3)"), 88.toDouble(), 0.000001)
        assertEquals(evaluateExpression("9%9"), 81 * 0.01, 0.000001)
        assertEquals(evaluateExpression("e5"), Math.E * 5, 0.000001)
        assertEquals(evaluateExpression("(9)(5)"), 45.toDouble(), 0.000001)
        assertEquals(evaluateExpression("(9)(5+6)(64)"), 6336.toDouble(), 0.000001)
        assertEquals(evaluateExpression("eπe5πeπe(5)"), Math.E.pow(4) * Math.PI.pow(3) * 25, 0.000001)
    }
}