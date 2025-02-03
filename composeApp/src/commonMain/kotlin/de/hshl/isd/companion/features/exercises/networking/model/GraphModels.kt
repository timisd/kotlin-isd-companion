package de.hshl.isd.companion.features.exercises.networking.model

data class Node(val id: String, val x: Float, val y: Float)
data class Edge(val from: Node, val to: Node, val weight: Int) 