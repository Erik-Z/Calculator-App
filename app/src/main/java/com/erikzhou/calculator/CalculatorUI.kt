package com.erikzhou.calculator

import androidx.compose.ui.Alignment
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.layout.size
import com.erikzhou.calculator.ui.theme.*
@Composable
fun CalculatorUI(
    viewModel: CalculatorViewModel,
) {
    val expression = viewModel.expression
    val buttonSpacing = 8.dp

    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val isTablet = maxWidth >= 600.dp
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
                            .padding(vertical = 8.dp, horizontal = 4.dp),
                        thresholdLength = if (isTablet) 10 else 7
                    )
                }
            }

            Divider(
                color = Color.LightGray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            for (row in listOf(
                listOf("π", "e", "", ""),
                listOf("AC", "()", "%", "/"),
                listOf("7", "8", "9", "*"),
                listOf("4", "5", "6", "-"),
                listOf("1", "2", "3", "+"),
                listOf("0", ".", "⌫", "=")
            )) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    for (button in row) {
                        CalculatorButton(
                            text = button,
                            modifier = Modifier
                                .aspectRatio(if (isTablet) 1.5f else 1f, matchHeightConstraintsFirst = true)
                                .weight(1f)
                                .clickable {
                                    when (button) {
                                        "π" -> viewModel.append("π")
                                        "e" -> viewModel.append("e")
                                        "AC" -> viewModel.clear()
                                        "()" -> viewModel.appendParentheses()
                                        "%" -> viewModel.append("%")
                                        "/" -> viewModel.append("/")
                                        "7" -> viewModel.append("7")
                                        "8" -> viewModel.append("8")
                                        "9" -> viewModel.append("9")
                                        "*" -> viewModel.append("*")
                                        "4" -> viewModel.append("4")
                                        "5" -> viewModel.append("5")
                                        "6" -> viewModel.append("6")
                                        "-" -> viewModel.append("-")
                                        "1" -> viewModel.append("1")
                                        "2" -> viewModel.append("2")
                                        "3" -> viewModel.append("3")
                                        "+" -> viewModel.append("+")
                                        "0" -> viewModel.append("0")
                                        "." -> viewModel.append(".")
                                        "⌫" -> viewModel.delete()
                                        "=" -> viewModel.evaluate()
                                    }
                                },
                            color = when (button) {
                                "AC" -> MaterialTheme.colorScheme.tertiary
                                "()" -> MaterialTheme.colorScheme.secondary
                                "%" -> MaterialTheme.colorScheme.secondary
                                "/" -> MaterialTheme.colorScheme.secondary
                                "*" -> MaterialTheme.colorScheme.secondary
                                "-" -> MaterialTheme.colorScheme.secondary
                                "+" -> MaterialTheme.colorScheme.secondary
                                "=" -> MaterialTheme.colorScheme.tertiary
                                "π" -> MaterialTheme.colorScheme.background
                                "e" -> MaterialTheme.colorScheme.background
                                "" -> MaterialTheme.colorScheme.background
                                else -> MaterialTheme.colorScheme.primary
                            }
                        )
                    }
                }
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
fun ScaledText(expression: String, modifier: Modifier = Modifier, thresholdLength: Int = 7) {
    val maxFontSize = 80
    val minFontSize = 40
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
            .padding(vertical = 8.dp, horizontal = 4.dp),
        fontWeight = FontWeight.Light,
        fontSize = fontSize.sp,
        color = MaterialTheme.colorScheme.onBackground,
        maxLines = 1
    )
}