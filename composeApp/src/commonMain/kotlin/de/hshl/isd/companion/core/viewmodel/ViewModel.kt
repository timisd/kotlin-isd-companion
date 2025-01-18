package de.hshl.isd.companion.core.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

open class ViewModel {
    val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
}