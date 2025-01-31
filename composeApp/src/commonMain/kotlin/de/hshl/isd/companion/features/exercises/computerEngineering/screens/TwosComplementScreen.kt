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
fun TwosComplementScreen() {
    val navigator = LocalNavigator.currentOrThrow
    var showSolution by remember { mutableStateOf(false) }
    var binaryNumber by remember { mutableStateOf(generateRandomBinary()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(Strings.get("twos_complement", currentLanguage)) },
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
                text = Strings.get("convert_to_twos_complement", currentLanguage),
                style = MaterialTheme.typography.headlineSmall
            )
            
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "${Strings.get("original_binary", currentLanguage)}:",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = binaryNumber,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    HorizontalDivider()
                    
                    if (showSolution) {
                        Text(
                            text = Strings.get("step_by_step", currentLanguage),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        
                        // Step 1: Original number
                        Text(
                            text = "${Strings.get("step_1", currentLanguage)}:",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = binaryNumber,
                            color = MaterialTheme.colorScheme.primary
                        )
                        
                        // Step 2: Inverted bits
                        Text(
                            text = "${Strings.get("step_2", currentLanguage)}:",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = invertBits(binaryNumber),
                            color = MaterialTheme.colorScheme.primary
                        )
                        
                        // Step 3: Add 1
                        Text(
                            text = "${Strings.get("step_3", currentLanguage)}:",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = calculateTwosComplement(binaryNumber),
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
                        binaryNumber = generateRandomBinary()
                        showSolution = false
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = Strings.get("new_number", currentLanguage),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(Strings.get("new_number", currentLanguage))
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
    return Random.nextInt(0, 128).toString(2).padStart(8, '0')
}

private fun invertBits(binary: String): String {
    return binary.map { if (it == '0') '1' else '0' }.joinToString("")
}

private fun calculateTwosComplement(binary: String): String {
    val inverted = invertBits(binary)
    var carry = 1
    val result = StringBuilder()
    
    // Add 1 to the inverted number
    for (i in inverted.length - 1 downTo 0) {
        val bit = inverted[i].digitToInt()
        val sum = bit + carry
        result.insert(0, sum % 2)
        carry = sum / 2
    }
    
    return result.toString()
}