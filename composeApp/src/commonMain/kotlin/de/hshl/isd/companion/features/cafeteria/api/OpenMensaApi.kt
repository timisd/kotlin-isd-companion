package de.hshl.isd.companion.features.cafeteria.api

import de.hshl.isd.companion.features.cafeteria.model.DayMenu
import de.hshl.isd.companion.features.cafeteria.model.Meal
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class OpenMensaApi {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    private val mensaId = "196"

    suspend fun getMeals(date: String): List<DayMenu> {
        return try {
            val url = "https://openmensa.org/api/v2/canteens/$mensaId/days/$date/meals"
            val response = client.get(url)
            val meals = response.body<List<Meal>>()
            if (meals.isNotEmpty()) {
                listOf(DayMenu(date = date, meals = meals))
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
} 