package de.hshl.isd.companion.features.cafeteria.viewmodel

import de.hshl.isd.companion.core.viewmodel.ViewModel
import de.hshl.isd.companion.features.cafeteria.api.OpenMensaApi
import de.hshl.isd.companion.features.cafeteria.model.DayMenu
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

class CafeteriaViewModel : ViewModel() {
    private val api = OpenMensaApi()
    private val _menuState = MutableStateFlow<CafeteriaUiState>(CafeteriaUiState.Loading)
    val menuState: StateFlow<CafeteriaUiState> = _menuState.asStateFlow()

    private val _currentDate =
        MutableStateFlow(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date)

    val formattedDate: String
        get() {
            val currentDate = _currentDate.value
            val day = currentDate.dayOfMonth.toString().padStart(2, '0')
            val month = currentDate.monthNumber.toString().padStart(2, '0')
            val year = currentDate.year
            return "$day.$month.$year"
        }

    fun formatPrice(price: Double): String {
        val euros = price.toInt()
        val cents = ((price - euros) * 100).toInt()
        return "$euros,${cents.toString().padStart(2, '0')}"
    }

    init {
        loadMenu()
    }

    fun loadMenu() {
        viewModelScope.launch {
            _menuState.value = CafeteriaUiState.Loading
            try {
                val menu = api.getMeals(_currentDate.value.toString())
                _menuState.value = CafeteriaUiState.Success(menu)
            } catch (e: Exception) {
                _menuState.value = CafeteriaUiState.Error("Failed to load menu: ${e.message}")
            }
        }
    }

    fun navigateDate(forward: Boolean) {
        _currentDate.value = _currentDate.value.plus(DatePeriod(days = if (forward) 1 else -1))
        loadMenu()
    }
}

sealed class CafeteriaUiState {
    data object Loading : CafeteriaUiState()
    data class Success(val menu: List<DayMenu>) : CafeteriaUiState()
    data class Error(val message: String) : CafeteriaUiState()
} 