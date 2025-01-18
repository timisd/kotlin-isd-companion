package de.hshl.isd.companion.features.professors

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.School
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import de.hshl.isd.companion.features.professors.ProfessorsScreen

object ProfessorsTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Profs"
            val icon = rememberVectorPainter(Icons.Filled.School)

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
        Navigator(screen = ProfessorsScreen()) { navigator ->
            SlideTransition(navigator = navigator)
        }
    }
}