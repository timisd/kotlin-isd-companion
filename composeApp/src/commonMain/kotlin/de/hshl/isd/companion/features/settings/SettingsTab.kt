package de.hshl.isd.companion.features.settings

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import de.hshl.isd.companion.core.localization.LanguageManager.currentLanguage
import de.hshl.isd.companion.core.localization.Strings

object SettingsTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Filled.Settings)

            return remember(currentLanguage) {
                TabOptions(
                    index = 0u,
                    title = Strings.get("tab_settings", currentLanguage),
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(screen = SettingsScreen()) { navigator ->
            SlideTransition(navigator = navigator)
        }
    }
}