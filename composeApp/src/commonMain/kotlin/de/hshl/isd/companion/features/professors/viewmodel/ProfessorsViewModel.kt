package de.hshl.isd.companion.features.professors.viewmodel

import de.hshl.isd.companion.core.storage.Storage
import de.hshl.isd.companion.core.viewmodel.ViewModel
import de.hshl.isd.companion.features.professors.api.ProfessorsApi
import de.hshl.isd.companion.features.professors.model.Professor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfessorsViewModel(storage: Storage) : ViewModel() {
    private val api = ProfessorsApi(storage)
    private val _uiState = MutableStateFlow<ProfessorsUiState>(ProfessorsUiState.Loading)
    val uiState: StateFlow<ProfessorsUiState> = _uiState.asStateFlow()

    init {
        loadProfessors()
    }

    fun loadProfessors(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.value = ProfessorsUiState.Loading
            try {
                val response = api.getProfessors(forceRefresh)
                _uiState.value = ProfessorsUiState.Success(response.professors)
            } catch (e: Exception) {
                _uiState.value = ProfessorsUiState.Error("Failed to load professors: ${e.message}")
            }
        }
    }
}

sealed class ProfessorsUiState {
    data object Loading : ProfessorsUiState()
    data class Success(val professors: List<Professor>) : ProfessorsUiState()
    data class Error(val message: String) : ProfessorsUiState()
} 