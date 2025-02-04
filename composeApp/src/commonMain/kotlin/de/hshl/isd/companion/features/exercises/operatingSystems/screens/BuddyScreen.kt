import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import de.hshl.isd.companion.core.localization.LanguageManager.currentLanguage
import de.hshl.isd.companion.core.localization.Strings
import androidx.compose.foundation.border
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

data class MemoryBlock(
    val size: Int,    // Size in KB
    val id: Char?,    // A, B, C, D, E or null if free
    val start: Int    // Start address in KB
)

data class MemoryState(
    val description: String,
    val blocks: List<MemoryBlock>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuddyScreen() {
    val navigator = LocalNavigator.currentOrThrow
    var showSolution by remember { mutableStateOf(false) }
    var memoryStates by remember { mutableStateOf(generateMemoryStates()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(Strings.get("buddy", currentLanguage)) },
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
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = Strings.get("buddy_exercise_description", currentLanguage),
                style = MaterialTheme.typography.headlineSmall
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Header row with memory sizes
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text("0", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                        Text("128k", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                        Text("256k", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                        Text("512k", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                    }

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(1.dp)
                    ) {
                        items(memoryStates) { state ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(1.dp, Color.Gray),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                // Description column
                                Box(
                                    modifier = Modifier
                                        .weight(0.5f)
                                        .border(1.dp, Color.Gray)
                                        .padding(4.dp)
                                ) {
                                    Text(
                                        state.description,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                }

                                if (showSolution) {
                                    state.blocks.forEach { block ->
                                        Box(
                                            modifier = Modifier
                                                .weight(block.size.toFloat())
                                                .border(1.dp, Color.Gray)
                                                .padding(4.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = block.id?.toString() ?: block.size.toString(),
                                                textAlign = TextAlign.Center,
                                                color = if (block.id != null)
                                                    MaterialTheme.colorScheme.primary
                                                else
                                                    MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        memoryStates = generateMemoryStates()
                        showSolution = false
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = Strings.get("reset", currentLanguage),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(Strings.get("reset", currentLanguage))
                }
                Switch(
                    checked = showSolution,
                    onCheckedChange = { showSolution = it }
                )
            }

            if (!showSolution) {
                Text(
                    text = Strings.get("show_solution_buddy", currentLanguage),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

private fun generateMemoryStates(): List<MemoryState> {
    val states = mutableListOf<MemoryState>()
    
    // Initial state - one 1024k block
    states.add(MemoryState(
        "start",
        listOf(MemoryBlock(1024, null, 0))
    ))

    // A = 70k -> needs 128k block
    states.add(MemoryState(
        "A = 70k",
        listOf(
            MemoryBlock(128, 'A', 0),
            MemoryBlock(128, null, 128),
            MemoryBlock(256, null, 256),
            MemoryBlock(512, null, 512)
        )
    ))

    // B = 35k -> needs 64k block
    states.add(MemoryState(
        "B = 35k",
        listOf(
            MemoryBlock(128, 'A', 0),
            MemoryBlock(64, 'B', 128),
            MemoryBlock(64, null, 192),
            MemoryBlock(256, null, 256),
            MemoryBlock(512, null, 512)
        )
    ))

    // C = 80k -> needs 128k block
    states.add(MemoryState(
        "C = 80k",
        listOf(
            MemoryBlock(128, 'A', 0),
            MemoryBlock(64, 'B', 128),
            MemoryBlock(64, null, 192),
            MemoryBlock(128, 'C', 256),
            MemoryBlock(128, null, 384),
            MemoryBlock(512, null, 512)
        )
    ))

    // A ends
    states.add(MemoryState(
        "A ends",
        listOf(
            MemoryBlock(128, null, 0),
            MemoryBlock(64, 'B', 128),
            MemoryBlock(64, null, 192),
            MemoryBlock(128, 'C', 256),
            MemoryBlock(128, null, 384),
            MemoryBlock(512, null, 512)
        )
    ))

    // D = 60k -> needs 64k block
    states.add(MemoryState(
        "D = 60k",
        listOf(
            MemoryBlock(128, null, 0),
            MemoryBlock(64, 'B', 128),
            MemoryBlock(64, 'D', 192),
            MemoryBlock(128, 'C', 256),
            MemoryBlock(128, null, 384),
            MemoryBlock(512, null, 512)
        )
    ))

    return states
} 