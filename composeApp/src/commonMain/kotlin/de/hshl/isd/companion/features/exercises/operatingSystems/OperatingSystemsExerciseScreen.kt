package de.hshl.isd.companion.features.exercises.operatingSystems

import BuddyScreen
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import de.hshl.isd.companion.features.exercises.operatingSystems.screens.PageReplacementScreen
import de.hshl.isd.companion.features.exercises.operatingSystems.screens.QuizScreen
import de.hshl.isd.companion.features.exercises.operatingSystems.screens.RealtimeSchedulingScreen
import de.hshl.isd.companion.features.exercises.operatingSystems.screens.SchedulingScreen

class OperatingSystemsExerciseScreen(private val exercise: OperatingSystemsExercises) : Screen {
    @Composable
    override fun Content() {
        when (exercise) {
            OperatingSystemsExercises.PAGE_REPLACEMENT -> PageReplacementScreen()
            OperatingSystemsExercises.BUDDY -> BuddyScreen()
            OperatingSystemsExercises.SCHEDULING -> SchedulingScreen()
            OperatingSystemsExercises.REALTIME_SCHEDULING -> RealtimeSchedulingScreen()
            OperatingSystemsExercises.QUIZ -> QuizScreen()
        }
    }
} 