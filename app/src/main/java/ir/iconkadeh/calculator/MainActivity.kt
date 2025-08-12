package ir.iconkadeh.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ir.iconkadeh.calculator.ui.theme.CalculatorTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val sharedPreferences = getSharedPreferences("History", MODE_PRIVATE)
            val calculatorLogic = CalculatorLogic(sharedPreferences)
            CalculatorTheme {
                MainScreen(calculatorLogic)
            }
        }
    }

}