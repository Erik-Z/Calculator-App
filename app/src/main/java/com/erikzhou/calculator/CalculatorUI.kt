package com.erikzhou.calculator

import androidx.compose.ui.Alignment
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import com.erikzhou.calculator.ui.theme.*
@Composable
fun CalculatorUI(
    viewModel: CalculatorViewModel,
) {
    val expression = viewModel.expression
    val buttonSpacing = 8.dp

    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = bottomPadding),
            verticalArrangement = Arrangement.spacedBy(buttonSpacing),
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth(),
                reverseLayout = true
            ) {
                item {
                    ScaledText(
                        expression = expression.value,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp, horizontal = 8.dp)
                    )
                }
            }

            Divider(
                color = Color.LightGray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(
                    text = "π",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            viewModel.append("π")
                        },
                    color = MaterialTheme.colorScheme.background,
                )
                CalculatorButton(
                    text = "e",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            viewModel.append("e")
                        },
                    color = MaterialTheme.colorScheme.background,
                )
                CalculatorButton(
                    text = "",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {

                        },
                    color = MaterialTheme.colorScheme.background,
                )
                CalculatorButton(
                    text = "",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {

                        },
                    color = MaterialTheme.colorScheme.background,
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(
                    text = "AC",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            viewModel.clear()
                        },
                    color = MaterialTheme.colorScheme.tertiary,
                )
                CalculatorButton(
                    text = "()",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            viewModel.appendParentheses()
                        },
                    color = MaterialTheme.colorScheme.secondary,
                )
                CalculatorButton(
                    text = "%",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {

                        },
                    color = MaterialTheme.colorScheme.secondary,
                )
                CalculatorButton(
                    text = "/",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            viewModel.append("/")
                        },
                    color = MaterialTheme.colorScheme.secondary,
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(
                    text = "7",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            viewModel.append("7")
                        },
                    color = MaterialTheme.colorScheme.primary,
                )
                CalculatorButton(
                    text = "8",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            viewModel.append("8")
                        },
                    color = MaterialTheme.colorScheme.primary,
                )
                CalculatorButton(
                    text = "9",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            viewModel.append("9")
                        },
                    color = MaterialTheme.colorScheme.primary,
                )
                CalculatorButton(
                    text = "*",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            viewModel.append("*")
                        },
                    color = MaterialTheme.colorScheme.secondary,
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(
                    text = "4",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            viewModel.append("4")
                        },
                    color = MaterialTheme.colorScheme.primary,
                )
                CalculatorButton(
                    text = "5",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            viewModel.append("5")
                        },
                    color = MaterialTheme.colorScheme.primary,
                )
                CalculatorButton(
                    text = "6",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            viewModel.append("6")
                        },
                    color = MaterialTheme.colorScheme.primary,
                )
                CalculatorButton(
                    text = "-",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            viewModel.append("-")
                        },
                    color = MaterialTheme.colorScheme.secondary,
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(
                    text = "1",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            viewModel.append("1")
                        },
                    color = MaterialTheme.colorScheme.primary,
                )
                CalculatorButton(
                    text = "2",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            viewModel.append("2")
                        },
                    color = MaterialTheme.colorScheme.primary,
                )
                CalculatorButton(
                    text = "3",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            viewModel.append("3")
                        },
                    color = MaterialTheme.colorScheme.primary,
                )
                CalculatorButton(
                    text = "+",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            viewModel.append("+")
                        },
                    color = MaterialTheme.colorScheme.secondary,
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(
                    text = "0",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            viewModel.append("0")
                        },
                    color = MaterialTheme.colorScheme.primary,
                )
                CalculatorButton(
                    text = ".",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            viewModel.append(".")
                        },
                    color = MaterialTheme.colorScheme.primary,
                )
                CalculatorButton(
                    text = "⌫",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            viewModel.delete()
                        },
                    color = MaterialTheme.colorScheme.primary,
                )
                CalculatorButton(
                    text = "=",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .clickable {
                            viewModel.evaluate()
                        },
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }
        }
    }
}

@Composable
fun CalculatorButton (
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    textStyle: TextStyle = TextStyle(),
    textColor: Color = MaterialTheme.colorScheme.onPrimary
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .background(color)
            .then(modifier)
    ) {
        Text(
            text = text,
            style = textStyle,
            fontSize = 36.sp,
            color = textColor
        )
    }
}

@Composable
fun ScaledText(expression: String, modifier: Modifier = Modifier) {
    val maxFontSize = 80
    val minFontSize = 40
    val thresholdLength = 8
    val length = expression.length

    val fontSize = when {
        length <= thresholdLength -> maxFontSize
        else -> {
            val scaleFactor = maxFontSize / thresholdLength
            (maxFontSize - (length - thresholdLength) * scaleFactor).coerceAtLeast(minFontSize)
        }
    }

    Text(
        text = expression,
        textAlign = TextAlign.End,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp, horizontal = 8.dp),
        fontWeight = FontWeight.Light,
        fontSize = fontSize.sp,
        color = MaterialTheme.colorScheme.onBackground,
        maxLines = 1
    )
}