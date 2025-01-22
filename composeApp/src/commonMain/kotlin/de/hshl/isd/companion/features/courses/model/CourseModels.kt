package de.hshl.isd.companion.features.courses.model

import de.hshl.isd.companion.features.professors.model.Professor
import kotlinx.serialization.Serializable

@Serializable
data class CoursesResponse(
    val courses: List<Course>,
    val last_updated: String
)

@Serializable
data class Course(
    val id: Int,
    val professor_id: Int,
    val name: String,
    val room: String,
    val start_time: String,
    val end_time: String,
    val day_of_week: String
)

data class CourseWithProfessor(
    val course: Course,
    val professor: Professor?
) {
    val professorName: String
        get() = professor?.let { "${it.title} ${it.lastName}" } ?: "Unknown Professor"
} 