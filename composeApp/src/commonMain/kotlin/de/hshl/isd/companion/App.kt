import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import de.hshl.isd.companion.core.localization.LanguageManager
import de.hshl.isd.companion.core.storage.LocalStorage
import de.hshl.isd.companion.core.storage.createStorage
import de.hshl.isd.companion.features.cafeteria.CafeteriaTab
import de.hshl.isd.companion.features.courses.CoursesTab
import de.hshl.isd.companion.features.exercises.ExercisesTab
import de.hshl.isd.companion.features.professors.ProfessorsTab
import de.hshl.isd.companion.features.settings.SettingsTab
import de.hshl.isd.companion.ui.CompanionTheme
import de.hshl.isd.companion.ui.ThemeManager

private val storage = createStorage()

@Composable
fun App() {
    CompositionLocalProvider(LocalStorage provides storage) {
        ThemeManager.initialize()
        LanguageManager.initialize()

        val isDarkMode = ThemeManager.isDarkMode

        CompanionTheme(darkTheme = isDarkMode) {
            Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier.fillMaxSize()
            ) {
                TabNavigator(tab = ExercisesTab) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = MaterialTheme.colorScheme.background,
                        bottomBar = {
                            Surface(
                                color = MaterialTheme.colorScheme.background,
                                modifier = Modifier
                            ) {
                                NavigationBar(
                                    containerColor = MaterialTheme.colorScheme.background,
                                    tonalElevation = 0.dp
                                ) {
                                    TabNavigationItem(CafeteriaTab)
                                    TabNavigationItem(ExercisesTab)
                                    TabNavigationItem(CoursesTab)
                                    TabNavigationItem(ProfessorsTab)
                                    TabNavigationItem(SettingsTab)
                                }
                            }
                        },
                        content = { CurrentTab() }
                    )
                }
            }
        }
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator: TabNavigator = LocalTabNavigator.current
    val isSelected = tabNavigator.current == tab

    NavigationBarItem(
        selected = isSelected,
        onClick = { tabNavigator.current = tab },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            indicatorColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        icon = {
            tab.options.icon?.let { icon ->
                Icon(
                    painter = icon,
                    contentDescription = tab.options.title,
                    modifier = Modifier
                )
            }
        },
        label = {
            Text(
                text = tab.options.title,
                style = MaterialTheme.typography.labelMedium
            )
        }
    )
}