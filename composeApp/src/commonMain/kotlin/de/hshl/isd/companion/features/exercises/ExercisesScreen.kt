package de.hshl.isd.companion.features.exercises

import NetworkingExerciseScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import de.hshl.isd.companion.core.localization.LanguageManager.currentLanguage
import de.hshl.isd.companion.core.localization.Strings
import de.hshl.isd.companion.features.exercises.computerEngineering.ComputerEngineeringExerciseScreen
import de.hshl.isd.companion.features.exercises.computerEngineering.ComputerEngineeringExercises
import de.hshl.isd.companion.features.exercises.networking.NetworkingExercises
import de.hshl.isd.companion.features.exercises.operatingSystems.OperatingSystemsExerciseScreen
import de.hshl.isd.companion.features.exercises.operatingSystems.OperatingSystemsExercises

class ExercisesScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Column(modifier = Modifier.fillMaxSize().padding(bottom = 80.dp)) {
            CategorySection(
                title = Strings.get("computer_engineering", currentLanguage),
                exercises = ComputerEngineeringExercises.entries,
                onExerciseClick = { exercise ->
                    navigator.push(ComputerEngineeringExerciseScreen(exercise))
                },
                modifier = Modifier.weight(1f)
            )

            CategorySection(
                title = Strings.get("networking", currentLanguage),
                exercises = NetworkingExercises.entries,
                onExerciseClick = { exercise ->
                    navigator.push(NetworkingExerciseScreen(exercise))
                },
                modifier = Modifier.weight(1f)
            )

            CategorySection(
                title = Strings.get("operating_systems", currentLanguage),
                exercises = OperatingSystemsExercises.entries,
                onExerciseClick = { exercise ->
                    navigator.push(OperatingSystemsExerciseScreen(exercise))
                },
                modifier = Modifier.weight(1f)
            )
        }
    }

    @Composable
    private fun <T : Enum<T>> CategorySection(
        title: String,
        exercises: List<T>,
        onExerciseClick: (T) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Column(modifier = modifier) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = title,
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.background
                )
            }

            LazyColumn(modifier = Modifier.fillMaxHeight()) {
                items(exercises) { exercise ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onExerciseClick(exercise) }
                            .padding(8.dp)
                    ) {
                        Text(
                            text = when (exercise) {
                                is OperatingSystemsExercises -> Strings.get(
                                    exercise.title,
                                    currentLanguage
                                )

                                is NetworkingExercises -> Strings.get(
                                    exercise.title,
                                    currentLanguage
                                )

                                is ComputerEngineeringExercises -> Strings.get(
                                    exercise.title,
                                    currentLanguage
                                )

                                else -> exercise.toString()
                            },
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.onPrimary)
                    )
                }
            }
        }
    }
}
