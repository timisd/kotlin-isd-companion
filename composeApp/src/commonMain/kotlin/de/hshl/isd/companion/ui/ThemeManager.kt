package de.hshl.isd.companion.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import de.hshl.isd.companion.core.storage.LocalStorage
import de.hshl.isd.companion.core.storage.Storage

object ThemeManager {
    private const val THEME_KEY = "theme_dark_mode"
    private var _isDarkMode = mutableStateOf(false)
    val isDarkMode: Boolean by _isDarkMode

    @Composable
    fun initialize() {
        val storage = LocalStorage.current

        LaunchedEffect(Unit) {
            val savedTheme = storage.getString(THEME_KEY)?.toBoolean() ?: false
            _isDarkMode.value = savedTheme
        }
    }

    suspend fun toggleTheme(darkMode: Boolean, storage: Storage? = null) {
        _isDarkMode.value = darkMode
        storage?.saveString(THEME_KEY, darkMode.toString())
    }
} 