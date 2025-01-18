package de.hshl.isd.companion.features.professors.model

import kotlinx.serialization.Serializable

@Serializable
data class ProfessorsResponse(
    val professors: List<Professor>,
    val last_updated: String
)

@Serializable
data class Professor(
    val id: Int,
    val title: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val imageUrl: String? = null  // Optional, with default value null
) 