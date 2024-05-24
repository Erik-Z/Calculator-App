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
        assertEquals(evaluateExpression("((1+2)*4)^(5+6)"), ((1 + 2) * 4).toDouble().pow(11.toDouble()), 0.000001)
        assertEquals(evaluateExpression("(5 + 6)(11 - 3)"), 88.toDouble(), 0.000001)
    }
}