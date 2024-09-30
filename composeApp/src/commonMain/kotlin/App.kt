import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import org.hshl.isd.companion.features.cafeteria.CafeteriaTab
import org.hshl.isd.companion.features.courses.CoursesTab
import org.hshl.isd.companion.features.exercises.ExercisesTab
import org.hshl.isd.companion.features.professors.ProfessorsTab
import org.hshl.isd.companion.features.settings.SettingsTab
import org.hshl.isd.companion.features.shared.DarkColorScheme
import org.hshl.isd.companion.features.shared.LightColorScheme

@Composable
fun App() {
    val isDarkTheme = isSystemInDarkTheme()
    val colorScheme = if (isDarkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(colorScheme = colorScheme) {
        TabNavigator(
            tab = CafeteriaTab
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    NavigationBar {
                        TabNavigationItem(CafeteriaTab)
                        TabNavigationItem(ExercisesTab)
                        TabNavigationItem(CoursesTab)
                        TabNavigationItem(ProfessorsTab)
                        TabNavigationItem(SettingsTab)
                    }
                },
                content = { CurrentTab() },
            )
        }
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator: TabNavigator = LocalTabNavigator.current

    NavigationBarItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = {
            tab.options.icon?.let { icon ->
                Icon(
                    painter = icon,
                    contentDescription =
                    tab.options.title
                )
            }
        },
        label = {
            Text(
                text = tab.options.title
            )
        }
    )
}