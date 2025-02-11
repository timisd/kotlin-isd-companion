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
import de.hshl.isd.companion.core.localization.LanguageManager.currentLanguage
import de.hshl.isd.companion.core.localization.Strings
import de.hshl.isd.companion.features.cafeteria.model.Meal
import de.hshl.isd.companion.features.cafeteria.viewmodel.CafeteriaUiState
import de.hshl.isd.companion.features.cafeteria.viewmodel.CafeteriaViewModel

class CafeteriaScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = remember { CafeteriaViewModel() }
        val state by viewModel.menuState.collectAsState()

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp, 16.dp, 16.dp, 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                        text = viewModel.formattedDate,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                IconButton(onClick = { viewModel.navigateDate(forward = true) }) {
                    Icon(Icons.AutoMirrored.Filled.NavigateNext, contentDescription = "Next day")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

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
                            modifier = Modifier.fillMaxSize().padding(bottom = 80.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(currentState.menu.flatMap { it.meals }) { meal ->
                                MealCard(meal = meal, viewModel = viewModel)
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
private fun MealCard(meal: Meal, viewModel: CafeteriaViewModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = meal.category,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = meal.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            meal.prices?.students?.let { price ->
                Text(
                    text = "${
                        Strings.get(
                            "student_price",
                            currentLanguage
                        )
                    }: ${viewModel.formatPrice(price)} €",
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