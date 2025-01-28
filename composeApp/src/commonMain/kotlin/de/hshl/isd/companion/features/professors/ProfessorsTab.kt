package de.hshl.isd.companion.features.professors

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.School
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import de.hshl.isd.companion.core.localization.LanguageManager
import de.hshl.isd.companion.core.localization.Strings

internal object ProfessorsTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Filled.School)
            val currentLanguage = LanguageManager.currentLanguage

            return remember(currentLanguage) {
                TabOptions(
                    index = 3u,
                    title = Strings.get("tab_professors", currentLanguage),
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        ProfessorsScreen().Content()
    }
}