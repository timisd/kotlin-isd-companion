package org.hshl.isd.companion.features.exercises

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition

object ExercisesTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Exercises"
            val icon = rememberVectorPainter(Icons.Default.Edit)

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
        Navigator(screen = ExercisesScreen()) { navigator ->
            SlideTransition(navigator = navigator)
        }
    }
}