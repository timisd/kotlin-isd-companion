package de.hshl.isd.companion.features.cafeteria

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import de.hshl.isd.companion.features.cafeteria.CafeteriaScreen

object CafeteriaTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Cafeteria"
            val icon = rememberVectorPainter(Icons.Filled.RestaurantMenu)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(screen = CafeteriaScreen()) { navigator ->
            SlideTransition(navigator = navigator)
        }
    }
}