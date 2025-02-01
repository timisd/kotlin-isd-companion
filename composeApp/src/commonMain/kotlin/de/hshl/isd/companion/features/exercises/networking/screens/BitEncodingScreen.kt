package de.hshl.isd.companion.features.exercises.networking.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import de.hshl.isd.companion.core.localization.LanguageManager.currentLanguage
import de.hshl.isd.companion.core.localization.Strings
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BitEncodingScreen() {
    val navigator = LocalNavigator.currentOrThrow
    var showSolution by remember { mutableStateOf(false) }
    var binarySequence by remember { mutableStateOf(generateRandomBits()) }
    var selectedEncoding by remember { mutableStateOf(EncodingType.NRZ) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(Strings.get("bit_encoding", currentLanguage)) },
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
                text = Strings.get("encode_binary_sequence", currentLanguage),
                style = MaterialTheme.typography.headlineSmall
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${Strings.get("binary_sequence", currentLanguage)}:",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = binarySequence,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    // Encoding type selector
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        EncodingType.values().forEach { type ->
                            FilterChip(
                                selected = selectedEncoding == type,
                                onClick = { selectedEncoding = type },
                                label = { Text(Strings.get(type.translationKey, currentLanguage)) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        }
                    }

                    if (showSolution) {
                        Spacer(modifier = Modifier.height(16.dp))
                        SignalDisplay(
                            binarySequence = binarySequence,
                            encodingType = selectedEncoding,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
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
                        binarySequence = generateRandomBits()
                        showSolution = false
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = Strings.get("new_sequence", currentLanguage),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(Strings.get("new_sequence", currentLanguage))
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

@Composable
private fun SignalDisplay(
    binarySequence: String,
    encodingType: EncodingType,
    modifier: Modifier = Modifier
) {
    // Get the primary color from the theme
    val primaryColor = MaterialTheme.colorScheme.primary

    Box(modifier = modifier) {
        Column {
            // Graph
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                val width = size.width
                val height = size.height
                val bitWidth = width / binarySequence.length
                val centerY = height / 2

                // Draw baseline
                drawLine(
                    color = Color.Gray,
                    start = Offset(0f, centerY),
                    end = Offset(width, centerY),
                    strokeWidth = 1f
                )

                // Draw vertical lines
                binarySequence.forEachIndexed { index, _ ->
                    val x = index * bitWidth
                    drawLine(
                        color = Color.Gray,
                        start = Offset(x, centerY - 50f),
                        end = Offset(x, centerY + 50f),
                        strokeWidth = 1f
                    )
                }
                // Draw final vertical line
                drawLine(
                    color = Color.Gray,
                    start = Offset(width, centerY - 50f),
                    end = Offset(width, centerY + 50f),
                    strokeWidth = 1f
                )

                when (encodingType) {
                    EncodingType.NRZ -> drawNRZ(binarySequence, bitWidth, centerY, primaryColor)
                    EncodingType.NRZ_I -> drawNRZI(binarySequence, bitWidth, centerY, primaryColor)
                    EncodingType.MLT_3 -> drawMLT3(binarySequence, bitWidth, centerY, primaryColor)
                }
            }

            // Binary and encoded sequences
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Binary sequence
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    binarySequence.forEach { bit ->
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = bit.toString(),
                                color = Color.Gray,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }

                // Encoded sequence
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    val encodedSequence = when (encodingType) {
                        EncodingType.NRZ -> getNRZSymbols(binarySequence)
                        EncodingType.NRZ_I -> getNRZISymbols(binarySequence)
                        EncodingType.MLT_3 -> getMLT3Symbols(binarySequence)
                    }
                    encodedSequence.forEach { symbol ->
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = symbol,
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun getNRZSymbols(sequence: String): List<String> {
    return sequence.map { bit ->
        if (bit == '1') "+" else "-"
    }
}

private fun getNRZISymbols(sequence: String): List<String> {
    var currentLevel = false // false = -, true = +
    return sequence.map { bit ->
        if (bit == '1') {
            currentLevel = !currentLevel
        }
        if (currentLevel) "+" else "-"
    }
}

private fun getMLT3Symbols(sequence: String): List<String> {
    var currentState = 0 // 0 = zero, 1 = positive, -1 = negative
    var nextUp = true   // true means next 1 goes up, false means next 1 goes down

    return sequence.map { bit ->
        if (bit == '1') {
            currentState = when (currentState) {
                0 -> if (nextUp) 1 else -1
                1, -1 -> {
                    nextUp = currentState == -1
                    0
                }

                else -> 0
            }
        }
        when (currentState) {
            1 -> "+"
            -1 -> "-"
            else -> "0"
        }
    }
}

private fun generateRandomBits(): String {
    return buildString {
        repeat(10) {
            append(Random.nextInt(2))
        }
    }
}

enum class EncodingType(val translationKey: String) {
    NRZ("nrz_encoding"),
    NRZ_I("nrz_i_encoding"),
    MLT_3("mlt_3_encoding")
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawNRZ(
    sequence: String,
    bitWidth: Float,
    centerY: Float,
    color: Color
) {
    val path = Path()
    var currentX = 0f
    val amplitude = 40f

    sequence.forEach { bit ->
        val y = if (bit == '1') centerY - amplitude else centerY + amplitude
        if (currentX == 0f) {
            path.moveTo(currentX, y)
        } else {
            path.lineTo(currentX, y)
        }
        path.lineTo(currentX + bitWidth, y)
        currentX += bitWidth
    }

    drawPath(
        path = path,
        color = color,
        style = Stroke(width = 2f)
    )
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawNRZI(
    sequence: String,
    bitWidth: Float,
    centerY: Float,
    color: Color
) {
    val path = Path()
    var currentX = 0f
    val amplitude = 40f
    var currentLevel = false // false = low, true = high

    path.moveTo(currentX, if (currentLevel) centerY - amplitude else centerY + amplitude)

    sequence.forEach { bit ->
        if (bit == '1') {
            currentLevel = !currentLevel
        }
        val y = if (currentLevel) centerY - amplitude else centerY + amplitude
        path.lineTo(currentX + bitWidth, y)
        currentX += bitWidth
    }

    drawPath(
        path = path,
        color = color,
        style = Stroke(width = 2f)
    )
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawMLT3(
    sequence: String,
    bitWidth: Float,
    centerY: Float,
    color: Color
) {
    val path = Path()
    var currentX = 0f
    val amplitude = 40f
    var currentState = 0 // 0 = zero, 1 = positive, -1 = negative
    var nextUp = true   // true means next 1 goes up, false means next 1 goes down

    path.moveTo(currentX, centerY)

    sequence.forEach { bit ->
        if (bit == '1') {
            currentState = when (currentState) {
                0 -> if (nextUp) 1 else -1
                1, -1 -> {
                    nextUp = currentState == -1
                    0
                }

                else -> 0
            }
        }

        val y = when (currentState) {
            1 -> centerY - amplitude
            -1 -> centerY + amplitude
            else -> centerY
        }

        path.lineTo(currentX + bitWidth, y)
        currentX += bitWidth
    }

    drawPath(
        path = path,
        color = color,
        style = Stroke(width = 2f)
    )
}