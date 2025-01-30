package de.hshl.isd.companion.features.exercises.computerEngineering

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import de.hshl.isd.companion.features.exercises.computerEngineering.screens.BinaryAdditionScreen
import de.hshl.isd.companion.features.exercises.computerEngineering.screens.BinaryToDecimalScreen
import de.hshl.isd.companion.features.exercises.computerEngineering.screens.DecimalToBinaryScreen
import de.hshl.isd.companion.features.exercises.computerEngineering.screens.TwosComplementScreen

class ComputerEngineeringExerciseScreen(private val exercise: ComputerEngineeringExercises) :
    Screen {
    @Composable
    override fun Content() {
        when (exercise) {
            ComputerEngineeringExercises.BINARY_TO_DECIMAL -> BinaryToDecimalScreen()
            ComputerEngineeringExercises.DECIMAL_TO_BINARY -> DecimalToBinaryScreen()
            ComputerEngineeringExercises.BINARY_ADDITION -> BinaryAdditionScreen()
            ComputerEngineeringExercises.TWOS_COMPLEMENT -> TwosComplementScreen()
        }
    }
}