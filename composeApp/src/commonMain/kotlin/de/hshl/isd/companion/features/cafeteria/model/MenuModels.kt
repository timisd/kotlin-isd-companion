package de.hshl.isd.companion.features.cafeteria.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Meal(
    val id: Int,
    val name: String,
    val category: String,
    val prices: Prices? = null,
    val notes: List<String> = emptyList()
)

@Serializable
data class Prices(
    val students: Double? = null,
    val employees: Double? = null,
    val pupils: Double? = null,
    val others: Double? = null
)

@Serializable
data class DayMenu(
    val date: String,
    val meals: List<Meal>
) 