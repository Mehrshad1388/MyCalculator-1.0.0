package ir.iconkadeh.calculator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(calculatorLogic: CalculatorLogic) {
    val displayText = rememberSaveable {
        mutableStateOf("0")
    }
    val labelText = rememberSaveable {
        mutableStateOf("")
    }


    Scaffold { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Display(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(35f),
                displayText = displayText,
                labelText = labelText
            )

            HorizontalDivider(
                thickness = 2.dp
            )

            Keypad(
                modifier = Modifier
                    .weight(65f)
                    .fillMaxWidth()
                    .padding(12.dp),
                displayText = displayText,
                labelText = labelText,
                calculatorLogic = calculatorLogic
            )

        }

    }

    calculatorLogic.emptyToZero(displayText)
    if (displayText.value.last() != '.')
        displayText.value = calculatorLogic.removeAnAdditionalZero(displayText.value)

}