package org.hshl.isd.companion.features.professors

data class ProfessorModel(
    val id: Int?,
    val title: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val imageBytes: ByteArray? = null
)

fun ProfessorModel.matchesSearchQuery(query: String): Boolean {
    if (query.isEmpty()) return true
    
    return title.contains(query, ignoreCase = true) ||
            firstName.contains(query, ignoreCase = true) ||
            lastName.contains(query, ignoreCase = true)
}