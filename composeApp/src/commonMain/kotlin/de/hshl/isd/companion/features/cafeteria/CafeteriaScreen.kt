package de.hshl.isd.companion.features.cafeteria

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateBefore
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import de.hshl.isd.companion.core.localization.LanguageManager
import de.hshl.isd.companion.core.localization.Strings
import de.hshl.isd.companion.features.cafeteria.model.Meal
import de.hshl.isd.companion.features.cafeteria.viewmodel.CafeteriaUiState
import de.hshl.isd.companion.features.cafeteria.viewmodel.CafeteriaViewModel
import kotlinx.datetime.LocalDate

@Composable
private fun formatDateGerman(date: LocalDate): String {
    // Pad single digits with leading zero
    val day = date.dayOfMonth.toString().padStart(2, '0')
    val month = date.monthNumber.toString().padStart(2, '0')
    val year = date.year
    return "$day.$month.$year"
}

class CafeteriaScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = remember { CafeteriaViewModel() }
        val state by viewModel.menuState.collectAsState()
        val currentDate by viewModel.currentDate.collectAsState()
        val currentLanguage = LanguageManager.currentLanguage

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Date navigation row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.navigateDate(forward = false) }) {
                    Icon(
                        Icons.AutoMirrored.Filled.NavigateBefore,
                        contentDescription = "Previous day"
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = Strings.get("menu_for", currentLanguage),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = formatDateGerman(currentDate),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                IconButton(onClick = { viewModel.navigateDate(forward = true) }) {
                    Icon(Icons.AutoMirrored.Filled.NavigateNext, contentDescription = "Next day")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Existing menu content
            when (val currentState = state) {
                is CafeteriaUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                is CafeteriaUiState.Success -> {
                    if (currentState.menu.isEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = Strings.get("no_menu_available", currentLanguage),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(currentState.menu.flatMap { it.meals }) { meal ->
                                MealCard(meal = meal)
                            }
                        }
                    }
                }

                is CafeteriaUiState.Error -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = currentState.message,
                            color = MaterialTheme.colorScheme.error
                        )
                        Button(
                            onClick = { viewModel.loadMenu() },
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text(Strings.get("retry", currentLanguage))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MealCard(meal: Meal) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = meal.category,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = meal.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            meal.prices?.students?.let { price ->
                Text(
                    text = "Student price: €$price",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            if (meal.notes.isNotEmpty()) {
                Text(
                    text = meal.notes.joinToString(", "),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}