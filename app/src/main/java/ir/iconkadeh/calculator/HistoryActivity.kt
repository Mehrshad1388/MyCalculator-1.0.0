package ir.iconkadeh.calculator

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import ir.iconkadeh.calculator.ui.theme.CalculatorTheme

class HistoryActivity : ComponentActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            sharedPreferences = getSharedPreferences("History", MODE_PRIVATE)

            CalculatorTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text("تاریخچه")
                            },
                            navigationIcon = {
                                IconButton({

                                    val intent = Intent(context, MainActivity::class.java)
                                    context.startActivity(intent)

                                }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                        contentDescription = "back"
                                    )
                                }
                            }
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton({
                            deleteHistory()
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "delete history"
                            )
                        }
                    }
                ) { innerPadding ->

                    GetHistory(innerPadding)
                    limitingTheResult()

                }
            }
        }
    }

    @Composable
    fun CustomCard(
        modifier: Modifier = Modifier,
        mathExpression: String,
        result: String
    ) {

        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(130.dp)
                .padding(16.dp)
        ) {


            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = mathExpression,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.End,
                )

                Text(
                    text = result,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            }
        }

    }

    private fun deleteHistory() {
        sharedPreferences.edit {
            for (item in 0..sharedPreferences.getInt("results", -1)) {
                remove("math expression$item")
                remove("result$item")
                remove("results")
            }
        }

        val intent = Intent(this@HistoryActivity, MainActivity::class.java)
        this@HistoryActivity.startActivity(intent)
    }

    @Composable
    fun GetHistory(innerPadding: PaddingValues) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            for (item in sharedPreferences.getInt(
                "results",
                -1
            ) downTo sharedPreferences.getInt(
                "first result",
                1
            )) {

                item {
                    CustomCard(
                        mathExpression =
                            sharedPreferences.getString("math expression$item", "")
                                ?: "",
                        result =
                            sharedPreferences.getString("result$item", "") ?: ""
                    )
                }

            }
        }

    }

    private fun limitingTheResult(num: Int = CalculatorDefaults.NUMBER_OF_HISTORY_RESULTS) {

        if (sharedPreferences.getInt("results", -1) > num) {

            sharedPreferences.edit {

                remove("math expression${sharedPreferences.getInt("first result", 1)}")
                remove("result${sharedPreferences.getInt("first result", 1)}")
                putInt(
                    "first result",
                    sharedPreferences.getInt("first result", 1) + 1
                )

            }

        }
    }

}
