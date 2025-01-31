package de.hshl.isd.companion.features.courses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import de.hshl.isd.companion.core.localization.LanguageManager.currentLanguage
import de.hshl.isd.companion.core.localization.Strings
import de.hshl.isd.companion.core.storage.LocalStorage
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
                    text = Strings.get("schedule", currentLanguage),
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
    val dayNames = listOf(
        Strings.get("monday", currentLanguage),
        Strings.get("tuesday", currentLanguage),
        Strings.get("wednesday", currentLanguage),
        Strings.get("thursday", currentLanguage),
        Strings.get("friday", currentLanguage)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp, 16.dp, 72.dp)
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
            color = MaterialTheme.colorScheme.primary,
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
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = courseWithProf.course.room,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}