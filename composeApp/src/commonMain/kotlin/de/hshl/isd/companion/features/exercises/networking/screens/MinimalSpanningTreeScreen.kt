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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import de.hshl.isd.companion.core.localization.LanguageManager.currentLanguage
import de.hshl.isd.companion.core.localization.Strings
import kotlin.random.Random
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import de.hshl.isd.companion.features.exercises.networking.model.Node
import de.hshl.isd.companion.features.exercises.networking.model.Edge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MinimalSpanningTreeScreen() {
    val navigator = LocalNavigator.currentOrThrow
    var showSolution by remember { mutableStateOf(false) }
    var graph by remember { mutableStateOf(generateRandomGraph()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(Strings.get("minimal_spanning_tree", currentLanguage)) },
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = Strings.get("mst_exercise_description", currentLanguage),
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
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        graph = generateRandomGraph()
                        showSolution = false
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = Strings.get("generate_new_tree", currentLanguage),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(Strings.get("generate_new_tree", currentLanguage))
                }
                Switch(
                    checked = showSolution,
                    onCheckedChange = { showSolution = it }
                )
            }

            if (!showSolution) {
                Text(Strings.get("mst_solution_hint", currentLanguage))
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
    val (nodes, edges) = graph
    val mstEdges = if (showSolution) findMinimalSpanningTree(nodes, edges) else emptyList()
    val textMeasurer = rememberTextMeasurer()
    val orangeColor = MaterialTheme.colorScheme.primary
    
    Canvas(modifier = modifier.fillMaxSize()) {
        // Draw edges first (so they appear behind nodes)
        edges.forEach { edge ->
            val start = Offset(edge.from.x * size.width, edge.from.y * size.height)
            val end = Offset(edge.to.x * size.width, edge.to.y * size.height)
            
            val isInSolution = mstEdges.contains(edge)
            val color = if (showSolution && isInSolution) orangeColor else Color.Gray
            
            drawLine(
                color = color,
                start = start,
                end = end,
                strokeWidth = if (isInSolution) 8f else 4f,
                cap = StrokeCap.Round
            )

            // Draw weight text directly (no rectangle)
            val midX = (start.x + end.x) / 2
            val midY = (start.y + end.y) / 2
            
            // Calculate text size for centering
            val weightText = edge.weight.toString()
            val textLayoutResult = textMeasurer.measure(
                text = weightText,
                style = TextStyle(
                    color = if (isInSolution) orangeColor else Color.Gray,
                    fontSize = 28.dp.toSp(),
                    textAlign = TextAlign.Center
                )
            )
            
            // Draw weight text with white background for better readability
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
    // Nodes in a more linear arrangement
    val nodes = listOf(
        Node("A", 0.15f, 0.5f),  // Left
        Node("B", 0.3f, 0.3f),   // Upper left
        Node("C", 0.5f, 0.5f),   // Center
        Node("D", 0.7f, 0.3f),   // Upper right
        Node("E", 0.85f, 0.5f),  // Right
        Node("F", 0.5f, 0.7f)    // Bottom center
    )

    // Create edges with collision detection
    val edges = mutableListOf<Edge>()
    val usedPositions = mutableSetOf<Pair<Float, Float>>() // Track weight positions

    // Helper function to check if a weight position would overlap
    fun isPositionAvailable(x: Float, y: Float): Boolean {
        val minDistance = 0.15f // Minimum distance between weight centers
        return usedPositions.none { (px, py) ->
            val distance = kotlin.math.sqrt((px - x) * (px - x) + (py - y) * (py - y))
            distance < minDistance
        }
    }

    // Helper function to find a valid position for weight
    fun findWeightPosition(x1: Float, y1: Float, x2: Float, y2: Float): Pair<Float, Float>? {
        val baseX = (x1 + x2) / 2
        val baseY = (y1 + y2) / 2
        
        // Try slightly different positions around the midpoint
        val offsets = listOf(
            0.0f to 0.0f,
            0.0f to 0.05f,
            0.0f to -0.05f,
            0.05f to 0.0f,
            -0.05f to 0.0f
        )

        for ((offsetX, offsetY) in offsets) {
            val newX = baseX + offsetX
            val newY = baseY + offsetY
            if (isPositionAvailable(newX, newY)) {
                return newX to newY
            }
        }
        return null
    }

    // Create a base set of edges that ensures connectivity
    val mandatoryEdges = listOf(
        nodes[0] to nodes[1], // A-B
        nodes[1] to nodes[2], // B-C
        nodes[2] to nodes[3], // C-D
        nodes[3] to nodes[4], // D-E
        nodes[2] to nodes[5]  // C-F
    )

    // Add mandatory edges first
    for ((from, to) in mandatoryEdges) {
        val weight = Random.nextInt(1, 101)
        val weightPos = findWeightPosition(from.x, from.y, to.x, to.y)
        if (weightPos != null) {
            edges.add(Edge(from, to, weight))
            usedPositions.add(weightPos)
        }
    }

    // Add additional random edges
    val possibleExtraEdges = mutableListOf<Pair<Node, Node>>()
    for (i in nodes.indices) {
        for (j in i + 1 until nodes.size) {
            val pair = nodes[i] to nodes[j]
            if (pair !in mandatoryEdges) {
                possibleExtraEdges.add(pair)
            }
        }
    }

    // Add 3-5 additional random edges
    possibleExtraEdges.shuffle()
    for ((from, to) in possibleExtraEdges.take(Random.nextInt(3, 6))) {
        val weightPos = findWeightPosition(from.x, from.y, to.x, to.y)
        if (weightPos != null) {
            val weight = Random.nextInt(1, 101)
            edges.add(Edge(from, to, weight))
            usedPositions.add(weightPos)
        }
    }

    return Pair(nodes, edges)
}

private fun findMinimalSpanningTree(nodes: List<Node>, edges: List<Edge>): List<Edge> {
    // Kruskal's algorithm implementation
    val sortedEdges = edges.sortedBy { it.weight }
    val parent = nodes.associateWith { it }.toMutableMap()
    val result = mutableListOf<Edge>()

    fun find(node: Node): Node {
        if (parent[node] != node) {
            parent[node] = find(parent[node]!!)
        }
        return parent[node]!!
    }

    fun union(node1: Node, node2: Node) {
        val parent1 = find(node1)
        val parent2 = find(node2)
        if (parent1 != parent2) {
            parent[parent2] = parent1
        }
    }

    for (edge in sortedEdges) {
        if (find(edge.from) != find(edge.to)) {
            result.add(edge)
            union(edge.from, edge.to)
        }
    }

    return result
}