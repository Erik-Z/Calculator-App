package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculator.ui.theme.CalculatorTheme
import com.example.calculator.Evaluator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val result = remember {
                mutableStateOf("0")
            }

            val num2 = remember {
                mutableStateOf("0")
            }

            val isNum2 = remember {
                mutableStateOf(false)
            }

            Column {
                val screenHeight = LocalConfiguration.current.screenHeightDp.dp
                val paddingPercentage = 0.05f
                val paddingDp = (screenHeight * paddingPercentage)
                TextField(
                    value = result.value,
                    onValueChange = { result.value = it },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = paddingDp),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    NumberButton("7", result)
                    NumberButton("8", result)
                    NumberButton("9", result)
                    OpButton("/", result)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    NumberButton("4", result)
                    NumberButton("5", result)
                    NumberButton("6", result)
                    OpButton("*", result)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    NumberButton("1", result)
                    NumberButton("2", result)
                    NumberButton("3", result)
                    OpButton("+", result)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    NumberButton("0", result)

                    Button(
                        onClick = { /* Handle button click */ },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(".")
                    }

                    Button(
                        onClick = { /* Handle button click */ },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("⌫")
                    }

                    Button(
                        onClick = {
                            println("Clicked")
                            println(Evaluator.infixToRPN(result.value))
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("=")
                    }
                }
            }
        }
    }
}

@Composable
fun RowScope.NumberButton (text: String, result: MutableState<String>){
Button(
    onClick = {
        if (result.value == "0") {
            result.value = text
        } else {
            result.value += text
        }
    },
    modifier = Modifier.weight(1f)
    ) {
        Text(text)
    }
}

@Composable
fun RowScope.OpButton (text: String, result: MutableState<String>){
    Button(
        onClick = {
            if(result.value.last() in listOf('+', '-', '*', '/', '^')){
                result.value = result.value.substring(0,result.value.length - 1) + text;
            } else {
                result.value += text
            }
        },
        modifier = Modifier.weight(1f)
    ) {
        Text(text)
    }
}