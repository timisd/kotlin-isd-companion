package de.hshl.isd.companion.core.localization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.hshl.isd.companion.core.storage.LocalStorage
import de.hshl.isd.companion.core.storage.Storage

object LanguageManager {
    private const val LANGUAGE_KEY = "app_language"
    private var _currentLanguage by mutableStateOf<String?>(null)
    val currentLanguage: String
        get() = _currentLanguage ?: "en"

    @Composable
    fun initialize() {
        val storage = LocalStorage.current

        LaunchedEffect(Unit) {
            val savedLanguage = storage.getString(LANGUAGE_KEY)
            _currentLanguage = savedLanguage ?: localeLanguage()
        }
    }

    suspend fun setLanguage(languageCode: String, storage: Storage? = null) {
        _currentLanguage = languageCode
        storage?.saveString(LANGUAGE_KEY, languageCode)
    }
} 