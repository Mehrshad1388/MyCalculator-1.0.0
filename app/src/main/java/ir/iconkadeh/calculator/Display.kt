package ir.iconkadeh.calculator

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Display(modifier: Modifier = Modifier, displayText: MutableState<String>, labelText: MutableState<String>) {

    val context = LocalContext.current

    Column(
        modifier = modifier
            .padding(15.dp)
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(11.dp)
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 15.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = labelText.value,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1
            )

            Text(
                text = displayText.value,
                fontSize = 42.sp,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1
            )

            Box(
                modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomStart
            ) {

                IconButton(
                    onClick = {
                        val intent = Intent(context, HistoryActivity::class.java)
                        context.startActivity(intent)
                    },
                ) {
                    Icon(
                        painter = painterResource(R.drawable.history_clock),
                        contentDescription = null
                    )
                }

                Box(
                    modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {


                        IconButton(
                            onClick = {
                                displayText.value = ""
                                labelText.value = ""
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.c),
                                contentDescription = null
                            )
                        }

                        Spacer(Modifier.width(5.dp))

                        IconButton(
                            onClick = {
                                displayText.value = displayText.value.dropLast(1)
                            },
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.backspace),
                                contentDescription = null
                            )
                        }

                    }
                }
            }
        }

    }

}
