package ir.iconkadeh.calculator

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.core.content.edit

class CalculatorLogic(private val sharedPreferences: SharedPreferences) {

    fun emptyToZero(displayText: MutableState<String>) {

        if (displayText.value.isEmpty()) {
            displayText.value = "0"
        }

        if (displayText.value.contains("Infinity")) {
            displayText.value = ""
        }

    }


    fun removeAnAdditionalZero(numberString: String): String {
        return numberString.replace(".0", "")
    }


    fun calculation(
        operators: Array<Char>,
        displayText: MutableState<String>,
        labelText: MutableState<String>
    ) {
        val regex = Regex("(?<=\\d)[${operators.joinToString("")}]")

        if (Regex("(?<=\\d)[${operators.joinToString("")}]").find(displayText.value) != null) {

            val numbers = displayText.value.split(regex)
            val operator = Regex("(?<=\\d)[${operators.joinToString("")}]").find(displayText.value)


            if (displayText.value.last() == '.')
                displayText.value = displayText.value.dropLast(1)



            val num2 = if (numbers[1] == "") numbers[0].toDouble() else numbers[1].toDouble()

            if (numbers[1] == "") {
                displayText.value += numbers[0]
            }

            Log.i("test", "1")
            Log.i("test", operator?.value ?: "null")

            var result = when (operator?.value) {
                "+" -> (numbers[0].toFloat() + num2).toString()
                "-" -> (numbers[0].toFloat() - num2).toString()
                "×" -> (numbers[0].toFloat() * num2).toString()
                "÷" -> (numbers[0].toFloat() / num2).toString()
                else -> ""
            }

            Log.i("test", "2")

            result = removeAnAdditionalZero(result)

            storage(displayText, result)
            labelText.value = displayText.value + "="
            displayText.value = result
        }

    }

    private fun storage(displayText: MutableState<String>, result: String) {

        sharedPreferences.edit {
            putInt(
                "results",
                sharedPreferences.getInt("results", 0) + 1
            )
            putInt(
                "first result",
                sharedPreferences.getInt("first result", 1)
            )
            putString(
                "math expression${sharedPreferences.getInt("results", 0) + 1}",
                displayText.value.replace("", " ") + " ="
            )
            putString(
                "result${sharedPreferences.getInt("results", 0) + 1}",
                result
            )
        }

    }

}

