package com.example.rolldice

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rolldice.ui.theme.RollDiceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RollDiceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RollDiceApp()
                }
            }
        }
    }
}

@Composable
fun DiceWithButtonAndImage( modifier: Modifier) {
    var result by remember { mutableStateOf(1) }
    val imageResource = when (result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }
    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(imageResource),
            contentDescription = result.toString()
        )
        Button(onClick = {
            result = (1..6).random()
            updatePool(result)
        }) {
            Text(stringResource(R.string.roll))
        }
        Column(
            modifier = Modifier
                .padding(vertical = 15.dp)
                .border(width = 1.dp, color = Color.Blue, shape = RoundedCornerShape(8.dp))
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 10.dp,
                alignment = Alignment.CenterVertically
            )
        ) {
            Text(stringResource(R.string.roll_1, rollResults[0]))
            Text(stringResource(R.string.roll_2, rollResults[1]))
            Text(stringResource(R.string.roll_3, rollResults[2]))
            Text(stringResource(R.string.roll_4, rollResults[3]))
            Text(stringResource(R.string.roll_5, rollResults[4]))
            Text(stringResource(R.string.roll_6, rollResults[5]))
        }
        val totalAmount = rollResults.sum()
        val listOfPercentages = listOf(
            (rollResults[0]/totalAmount.toFloat()),
            (rollResults[1]/totalAmount.toFloat()),
            (rollResults[2]/totalAmount.toFloat()),
            (rollResults[3]/totalAmount.toFloat()),
            (rollResults[4]/totalAmount.toFloat()),
            (rollResults[5]/totalAmount.toFloat()))

        PieChart(
            modifier = Modifier.padding(20.dp),
            colors = listOf(Color.Blue, Color.Red, Color.Green, Color.Cyan, Color.Yellow, Color.Gray),
            inputValues = listOfPercentages,
            textColor = Color.White
        )

    }
}

@Preview(showBackground = true)
@Composable
fun RollDiceApp() {
    DiceWithButtonAndImage(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)
        .verticalScroll(rememberScrollState())
    )
}

var rollResults = MutableList(6) { 1 }
fun updatePool(result: Int) {
    rollResults[result - 1] ++
}

