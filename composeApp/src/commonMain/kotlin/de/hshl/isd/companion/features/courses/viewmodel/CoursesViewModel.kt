package de.hshl.isd.companion.features.courses.viewmodel

import de.hshl.isd.companion.core.storage.Storage
import de.hshl.isd.companion.features.courses.api.CoursesApi
import de.hshl.isd.companion.features.courses.model.Course
import de.hshl.isd.companion.features.courses.model.CourseWithProfessor
import de.hshl.isd.companion.features.professors.api.ProfessorsApi
import de.hshl.isd.companion.features.professors.model.Professor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class CoursesUiState {
    data object Loading : CoursesUiState()
    data class Success(val coursesWithProfessors: List<CourseWithProfessor>) : CoursesUiState()
    data class Error(val message: String) : CoursesUiState()
}

class CoursesViewModel(storage: Storage) {
    private val coursesApi = CoursesApi(storage)
    private val professorsApi = ProfessorsApi(storage)
    private val viewModelScope = CoroutineScope(Dispatchers.Main)
    
    private val _uiState = MutableStateFlow<CoursesUiState>(CoursesUiState.Loading)
    val uiState: StateFlow<CoursesUiState> = _uiState

    init {
        loadCourses()
    }

    fun loadCourses(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.value = CoursesUiState.Loading
            try {
                val coursesResponse = coursesApi.getCourses(forceRefresh)
                val professorsResponse = professorsApi.getProfessors(forceRefresh)
                
                val coursesWithProfessors = coursesResponse.courses.map { course ->
                    val professor = professorsResponse.professors.find { it.id == course.professor_id }
                    CourseWithProfessor(course, professor)
                }
                
                _uiState.value = CoursesUiState.Success(coursesWithProfessors)
            } catch (e: Exception) {
                _uiState.value = CoursesUiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
} 