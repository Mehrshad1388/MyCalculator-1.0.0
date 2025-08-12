package ir.iconkadeh.calculator

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Keypad(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = CalculatorDefaults.ButtonShape,
    displayText: MutableState<String>,
    labelText: MutableState<String>,
    calculatorLogic: CalculatorLogic
) {

    val buttons = listOf(
        "7", "8", "9", "÷",
        "4", "5", "6", "×",
        "1", "2", "3", "-",
        "0", ".", "=", "+"
    )


    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        for (column in 0..3) {

            Column(
                modifier = Modifier.weight(0.25f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                for (item in 0..3) {

                    val char = buttons[((item) * 4) + column]
                    CustomButton(
                        modifier = Modifier
                            .weight(0.25f)
                            .fillMaxSize()
                            .padding(CalculatorDefaults.ButtonPadding),
                        text = char,
                        onClick = {
                            if (conditions(char[0], displayText, labelText, calculatorLogic))
                                displayText.value += char
                        },
                        shape = shape
                    )
                }
            }

        }

    }
}

fun conditions(
    char: Char,
    displayText: MutableState<String>,
    labelText: MutableState<String>,
    calculatorLogic: CalculatorLogic
): Boolean {

    var result = true

    val operators = arrayOf('×', '÷', '+', '-')

    if (operators.contains(char) && operators.contains(displayText.value.last())) {
        displayText.value = displayText.value.dropLast(1)
    }

    if (displayText.value.contains('.') && char == '.') {
        result = false
    }


    if (
        displayText.value.length == 1 &&
        displayText.value == "0" &&
        !operators.contains(char) &&
        char != '.'
    ) {
        displayText.value = ""
    }

    if (
        displayText.value.length == 1 &&
        displayText.value == "0" &&
        char == '-'
    ) {
        displayText.value = ""
    }


    if (char == '=') {
        calculatorLogic.calculation(operators, displayText, labelText)
        result = false
    }

    if (
        operators.contains(char) &&
        displayText.value.contains(Regex("(?<=\\d)[${operators.joinToString("")}]")) &&
        result
    ) {
        calculatorLogic.calculation(operators, displayText, labelText)
    }

    return result

}

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    shape: RoundedCornerShape
) {

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val isPressed by interactionSource.collectIsPressedAsState()

    val currentElevation by animateDpAsState(
        targetValue = if (isPressed) 2.dp else 8.dp
    )

    Button(
        onClick = {
            onClick()
        },
        modifier = modifier
            .shadow(
                elevation = currentElevation,
                shape = CalculatorDefaults.ButtonShape
            ),
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        interactionSource = interactionSource
    ) {
        Text(
            text = text,
            fontSize = 32.sp
        )
    }

}