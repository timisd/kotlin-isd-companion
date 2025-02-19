package de.hshl.isd.companion.features.exercises.computerEngineering.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import de.hshl.isd.companion.core.localization.LanguageManager.currentLanguage
import de.hshl.isd.companion.core.localization.Strings
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BinaryAdditionScreen() {
    val navigator = LocalNavigator.currentOrThrow
    var showSolution by remember { mutableStateOf(false) }
    var firstNumber by remember { mutableStateOf(generateRandomBinary()) }
    var secondNumber by remember { mutableStateOf(generateRandomBinary()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(Strings.get("binary_addition", currentLanguage)) },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = Strings.get("go_back", currentLanguage)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = Strings.get("add_binary_numbers", currentLanguage),
                style = MaterialTheme.typography.headlineSmall
            )
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = firstNumber,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Text(
                        text = "+ $secondNumber",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    if (showSolution) {
                        Text(
                            text = calculateBinarySum(firstNumber, secondNumber),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        firstNumber = generateRandomBinary()
                        secondNumber = generateRandomBinary()
                        showSolution = false
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = Strings.get("new_numbers", currentLanguage),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(Strings.get("new_numbers", currentLanguage))
                }
                Switch(
                    checked = showSolution,
                    onCheckedChange = { showSolution = it }
                )
            }
            if (!showSolution) {
                Text(Strings.get("show_solution_hint", currentLanguage))
            }
        }
    }
}

private fun generateRandomBinary(): String {
    return Random.nextInt(0, 256).toString(2).padStart(8, '0')
}

private fun calculateBinarySum(first: String, second: String): String {
    val num1 = first.toInt(2)
    val num2 = second.toInt(2)
    return (num1 + num2).toString(2).padStart(8, '0')
}