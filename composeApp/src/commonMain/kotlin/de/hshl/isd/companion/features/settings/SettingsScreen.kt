package de.hshl.isd.companion.features.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import de.hshl.isd.companion.core.localization.LanguageManager
import de.hshl.isd.companion.core.localization.LanguageManager.currentLanguage
import de.hshl.isd.companion.core.localization.Strings
import de.hshl.isd.companion.core.storage.LocalStorage
import de.hshl.isd.companion.ui.theme.ThemeManager
import kotlinx.coroutines.launch

class SettingsScreen : Screen {
    @Composable
    override fun Content() {
        var isDarkMode by remember { mutableStateOf(ThemeManager.isDarkMode) }
        val storage = LocalStorage.current
        val scope = rememberCoroutineScope()
        var showLanguageDialog by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Dark Mode Switch
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = Strings.get("dark_mode", currentLanguage),
                    style = MaterialTheme.typography.bodyLarge
                )
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = { newValue ->
                        isDarkMode = newValue
                        scope.launch {
                            ThemeManager.toggleTheme(newValue, storage)
                        }
                    }
                )
            }

            // Language Selection
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = Strings.get("language", currentLanguage),
                    style = MaterialTheme.typography.bodyLarge
                )

                Button(
                    onClick = { showLanguageDialog = true }
                ) {
                    Text(
                        Strings.supportedLanguages.find { it.code == currentLanguage }?.displayName
                            ?: ""
                    )
                }
            }
        }

        if (showLanguageDialog) {
            AlertDialog(
                onDismissRequest = { showLanguageDialog = false },
                title = { Text(Strings.get("language", currentLanguage)) },
                text = {
                    Column {
                        Strings.supportedLanguages.forEach { language ->
                            TextButton(
                                onClick = {
                                    scope.launch {
                                        LanguageManager.setLanguage(language.code, storage)
                                    }
                                    showLanguageDialog = false
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(language.displayName)
                            }
                        }
                    }
                },
                confirmButton = {},
                dismissButton = {
                    TextButton(onClick = { showLanguageDialog = false }) {
                        Text(Strings.get("cancel", currentLanguage))
                    }
                }
            )
        }
    }
}