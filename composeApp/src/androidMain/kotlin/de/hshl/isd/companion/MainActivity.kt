package de.hshl.isd.companion

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import de.hshl.isd.companion.ui.CompanionTheme
import de.hshl.isd.companion.ui.theme.ThemeManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompanionTheme(darkTheme = ThemeManager.isDarkMode) {
                SetStatusBarAndNavigationBarColor(window, ThemeManager.isDarkMode)
                App()
            }
        }
    }
}

@Composable
fun SetStatusBarAndNavigationBarColor(window: android.view.Window, darkTheme: Boolean) {
    val backgroundColor = MaterialTheme.colorScheme.background.toArgb()
    SideEffect {
        window.statusBarColor = backgroundColor
        window.navigationBarColor = backgroundColor

        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        if (darkTheme) {
            windowInsetsController.isAppearanceLightStatusBars = false
            windowInsetsController.isAppearanceLightNavigationBars = false
        } else {
            windowInsetsController.isAppearanceLightStatusBars = true
            windowInsetsController.isAppearanceLightNavigationBars = true
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}