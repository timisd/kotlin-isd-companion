package de.hshl.isd.companion.features.courses

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import de.hshl.isd.companion.core.storage.LocalStorage
import de.hshl.isd.companion.features.courses.model.Course
import de.hshl.isd.companion.features.courses.model.CourseWithProfessor
import de.hshl.isd.companion.features.courses.viewmodel.CoursesUiState
import de.hshl.isd.companion.features.courses.viewmodel.CoursesViewModel

class CoursesScreen : Screen {
    @Composable
    override fun Content() {
        val storage = LocalStorage.current
        val viewModel = remember { CoursesViewModel(storage) }
        val state by viewModel.uiState.collectAsState()

        Column(modifier = Modifier.fillMaxSize()) {
            // Header with refresh button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Stundenplan",
                    style = MaterialTheme.typography.headlineSmall
                )
                IconButton(onClick = { viewModel.loadCourses(forceRefresh = true) }) {
                    Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                }
            }

            when (val currentState = state) {
                is CoursesUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is CoursesUiState.Success -> {
                    WeeklySchedule(courses = currentState.coursesWithProfessors)
                }

                is CoursesUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = currentState.message,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WeeklySchedule(courses: List<CourseWithProfessor>) {
    val dayNames = listOf("Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag")
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(dayNames) { dayName ->
            val dayNumber = (dayNames.indexOf(dayName) + 1).toString()
            val daysCourses = courses.filter { it.course.day_of_week == dayNumber }
            
            if (daysCourses.isNotEmpty()) {
                DaySchedule(dayName = dayName, courses = daysCourses)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun DaySchedule(dayName: String, courses: List<CourseWithProfessor>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = dayName,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        courses.sortedBy { it.course.start_time }.forEach { courseWithProf ->
            CourseCard(courseWithProf = courseWithProf)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun CourseCard(courseWithProf: CourseWithProfessor) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = courseWithProf.course.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = courseWithProf.professorName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${courseWithProf.course.start_time} - ${courseWithProf.course.end_time}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = courseWithProf.course.room,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}