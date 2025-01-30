import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import de.hshl.isd.companion.features.exercises.networking.NetworkingExercises
import de.hshl.isd.companion.features.exercises.networking.screens.BitEncodingScreen
import de.hshl.isd.companion.features.exercises.networking.screens.CrcScreen
import de.hshl.isd.companion.features.exercises.networking.screens.MinimalSpantreeScreen
import de.hshl.isd.companion.features.exercises.networking.screens.NetmaskScreen
import de.hshl.isd.companion.features.exercises.networking.screens.QuizScreen
import de.hshl.isd.companion.features.exercises.networking.screens.ShortestPathScreen

class NetworkingExerciseScreen(private val exercise: NetworkingExercises) : Screen {
    @Composable
    override fun Content() {
        when (exercise) {
            NetworkingExercises.BIT_ENCODING -> BitEncodingScreen()
            NetworkingExercises.CRC -> CrcScreen()
            NetworkingExercises.MINIMAL_SPANTREE -> MinimalSpantreeScreen()
            NetworkingExercises.SHORTEST_PATH -> ShortestPathScreen()
            NetworkingExercises.NETMASK -> NetmaskScreen()
            NetworkingExercises.QUIZ -> QuizScreen()
        }
    }
}