package de.hshl.isd.companion.features.exercises.networking.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import de.hshl.isd.companion.core.localization.LanguageManager.currentLanguage
import de.hshl.isd.companion.core.localization.Strings
import kotlin.random.Random
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.TextMeasurer
import de.hshl.isd.companion.features.exercises.networking.model.Node
import de.hshl.isd.companion.features.exercises.networking.model.Edge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShortestPathScreen() {
    val navigator = LocalNavigator.currentOrThrow
    var showSolution by remember { mutableStateOf(false) }
    var graph by remember { mutableStateOf(generateRandomGraph()) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(Strings.get("shortest_path", currentLanguage)) },
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
                text = Strings.get("find_shortest_path", currentLanguage),
                style = MaterialTheme.typography.headlineSmall
            )
            
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    GraphCanvas(
                        graph = graph,
                        showSolution = showSolution
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("${Strings.get("start_node", currentLanguage)}: A")
                Text("${Strings.get("end_node", currentLanguage)}: F")
            }

            if (showSolution) {
                Text(
                    "${Strings.get("shortest_path_length", currentLanguage)}: ${calculateShortestPath(graph)}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        graph = generateRandomGraph()
                        showSolution = false
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = Strings.get("new_graph", currentLanguage),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(Strings.get("new_graph", currentLanguage))
                }
                Switch(
                    checked = showSolution,
                    onCheckedChange = { showSolution = it }
                )
            }

            if (!showSolution) {
                Text(Strings.get("show_solution_shortest_path", currentLanguage))
            }
        }
    }
}

@Composable
private fun GraphCanvas(
    graph: Pair<List<Node>, List<Edge>>,
    showSolution: Boolean,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()
    val orangeColor = MaterialTheme.colorScheme.primary
    
    Canvas(modifier = modifier.fillMaxSize()) {
        val (nodes, edges) = graph
        
        // Draw edges first (so they appear behind nodes)
        edges.forEach { edge ->
            val start = Offset(edge.from.x * size.width, edge.from.y * size.height)
            val end = Offset(edge.to.x * size.width, edge.to.y * size.height)
            
            val isInSolution = showSolution && isEdgeInShortestPath(edge, graph)
            val color = if (isInSolution) orangeColor else Color.Gray
            
            drawLine(
                color = color,
                start = start,
                end = end,
                strokeWidth = if (isInSolution) 8f else 4f,
                cap = StrokeCap.Round
            )

            // Draw weight text
            val midX = (start.x + end.x) / 2
            val midY = (start.y + end.y) / 2
            
            val weightText = edge.weight.toString()
            val textLayoutResult = textMeasurer.measure(
                text = weightText,
                style = TextStyle(
                    color = if (isInSolution) orangeColor else Color.Gray,
                    fontSize = 28.dp.toSp(),
                    textAlign = TextAlign.Center
                )
            )
            
            // Draw weight text with background for better readability
            val padding = 8f
            drawRect(
                color = Color.Black.copy(alpha = 0.5f),
                topLeft = Offset(
                    midX - textLayoutResult.size.width/2 - padding,
                    midY - textLayoutResult.size.height/2 - padding
                ),
                size = androidx.compose.ui.geometry.Size(
                    textLayoutResult.size.width + padding * 2,
                    textLayoutResult.size.height + padding * 2
                )
            )
            
            drawText(
                textMeasurer = textMeasurer,
                text = weightText,
                style = TextStyle(
                    color = if (isInSolution) orangeColor else Color.White,
                    fontSize = 28.dp.toSp(),
                    textAlign = TextAlign.Center
                ),
                topLeft = Offset(
                    midX - textLayoutResult.size.width/2,
                    midY - textLayoutResult.size.height/2
                )
            )
        }

        // Draw nodes on top
        nodes.forEach { node ->
            val center = Offset(node.x * size.width, node.y * size.height)
            
            // Larger node circles
            drawCircle(
                color = Color.White,
                radius = 45f,
                center = center
            )
            drawCircle(
                color = Color.Gray,
                radius = 45f,
                center = center,
                style = Stroke(width = 2f)
            )
            
            // Measure text for proper centering
            val textLayoutResult = textMeasurer.measure(
                text = node.id,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 36.dp.toSp(),
                    textAlign = TextAlign.Center
                )
            )
            
            // Draw node label perfectly centered
            drawText(
                textMeasurer = textMeasurer,
                text = node.id,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 36.dp.toSp(),
                    textAlign = TextAlign.Center
                ),
                topLeft = Offset(
                    center.x - textLayoutResult.size.width/2,
                    center.y - textLayoutResult.size.height/2
                )
            )
        }
    }
}

private fun generateRandomGraph(): Pair<List<Node>, List<Edge>> {
    // Fixed node positions for consistent layout
    val nodes = listOf(
        Node("A", 0.15f, 0.2f),
        Node("B", 0.5f, 0.2f),
        Node("C", 0.85f, 0.2f),
        Node("D", 0.15f, 0.8f),
        Node("E", 0.5f, 0.8f),
        Node("F", 0.85f, 0.8f)
    )
    
    // Generate random weights between 1 and 9 for each edge
    val edges = listOf(
        Edge(nodes[0], nodes[1], Random.nextInt(1, 10)),  // A-B
        Edge(nodes[1], nodes[2], Random.nextInt(1, 10)),  // B-C
        Edge(nodes[0], nodes[3], Random.nextInt(1, 10)),  // A-D
        Edge(nodes[1], nodes[4], Random.nextInt(1, 10)),  // B-E
        Edge(nodes[2], nodes[5], Random.nextInt(1, 10)),  // C-F
        Edge(nodes[3], nodes[4], Random.nextInt(1, 10)),  // D-E
        Edge(nodes[4], nodes[5], Random.nextInt(1, 10))   // E-F
    )
    
    return Pair(nodes, edges)
}

private fun calculateShortestPath(graph: Pair<List<Node>, List<Edge>>): Int {
    val (nodes, edges) = graph
    
    // Implementation of Dijkstra's algorithm
    val distances = mutableMapOf<String, Int>()
    val previous = mutableMapOf<String, String?>()
    val unvisited = nodes.map { it.id }.toMutableSet()
    
    // Initialize distances
    nodes.forEach { node ->
        distances[node.id] = if (node.id == "A") 0 else Int.MAX_VALUE
    }
    
    while (unvisited.isNotEmpty()) {
        // Find node with minimum distance
        val current = unvisited.minByOrNull { distances[it] ?: Int.MAX_VALUE } ?: break
        if (current == "F") break // Target reached
        
        unvisited.remove(current)
        
        // Find all edges connected to current node
        edges.filter { it.from.id == current || it.to.id == current }.forEach { edge ->
            val neighbor = if (edge.from.id == current) edge.to.id else edge.from.id
            if (neighbor in unvisited) {
                val newDist = (distances[current] ?: Int.MAX_VALUE) + edge.weight
                if (newDist < (distances[neighbor] ?: Int.MAX_VALUE)) {
                    distances[neighbor] = newDist
                    previous[neighbor] = current
                }
            }
        }
    }
    
    return distances["F"] ?: Int.MAX_VALUE
}

private fun isEdgeInShortestPath(edge: Edge, graph: Pair<List<Node>, List<Edge>>): Boolean {
    val (nodes, edges) = graph
    
    // Find the shortest path using Dijkstra's algorithm
    val path = mutableListOf<String>()
    val distances = mutableMapOf<String, Int>()
    val previous = mutableMapOf<String, String?>()
    val unvisited = nodes.map { it.id }.toMutableSet()
    
    // Initialize distances
    nodes.forEach { node ->
        distances[node.id] = if (node.id == "A") 0 else Int.MAX_VALUE
    }
    
    while (unvisited.isNotEmpty()) {
        val current = unvisited.minByOrNull { distances[it] ?: Int.MAX_VALUE } ?: break
        if (current == "F") break
        
        unvisited.remove(current)
        
        edges.filter { it.from.id == current || it.to.id == current }.forEach { e ->
            val neighbor = if (e.from.id == current) e.to.id else e.from.id
            if (neighbor in unvisited) {
                val newDist = (distances[current] ?: Int.MAX_VALUE) + e.weight
                if (newDist < (distances[neighbor] ?: Int.MAX_VALUE)) {
                    distances[neighbor] = newDist
                    previous[neighbor] = current
                }
            }
        }
    }
    
    // Reconstruct path
    var current: String? = "F"
    while (current != null) {
        path.add(0, current)
        current = previous[current]
    }
    
    // Check if this edge is in the shortest path
    for (i in 0 until path.size - 1) {
        if ((edge.from.id == path[i] && edge.to.id == path[i + 1]) ||
            (edge.from.id == path[i + 1] && edge.to.id == path[i])) {
            return true
        }
    }
    
    return false
}