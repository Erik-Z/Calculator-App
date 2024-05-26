package com.erikzhou.calculator

import org.junit.Assert
import org.junit.Test

class ViewModelUnitTest {

    fun testViewModelStateAppend(initialState: String, appendChar: String): String{
        val viewModel = CalculatorViewModel()
        viewModel.expression.value = initialState
        viewModel.append(appendChar)
        return viewModel.expression.value
    }
    @Test
    fun testNormalOperatorsAppend(){
        Assert.assertEquals(testViewModelStateAppend("9", "*"), "9*",)
        Assert.assertEquals(testViewModelStateAppend("9", "+"), "9+",)
        Assert.assertEquals(testViewModelStateAppend("9", "/"), "9/",)
        Assert.assertEquals(testViewModelStateAppend("9", "-"), "9-",)
        Assert.assertEquals(testViewModelStateAppend("9*", "*"), "9*",)
        Assert.assertEquals(testViewModelStateAppend("9*", "+"), "9+",)
        Assert.assertEquals(testViewModelStateAppend("9*", "/"), "9/",)
        Assert.assertEquals(testViewModelStateAppend("9*", "-"), "9*-",)
        Assert.assertEquals(testViewModelStateAppend("9*-", "-"), "9*-",)
        Assert.assertEquals(testViewModelStateAppend("9*-", "*"), "9*",)
        Assert.assertEquals(testViewModelStateAppend("9-", "*"), "9*",)
    }
}