package de.hshl.isd.companion.features.courses.api

import de.hshl.isd.companion.core.storage.Storage
import de.hshl.isd.companion.features.courses.model.CoursesResponse
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class CoursesApi(private val storage: Storage) {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(json)
        }
    }

    private val apiUrl =
        "https://raw.githubusercontent.com/timisd/kotlin-isd-companion/main/resources/courses.json"
    private val STORAGE_KEY_DATA = "courses_data"
    private val STORAGE_KEY_LAST_UPDATED = "courses_last_updated"

    suspend fun getCourses(forceRefresh: Boolean = false): CoursesResponse {
        if (!forceRefresh) {
            val cachedData = storage.getString(STORAGE_KEY_DATA)
            if (cachedData != null) {
                try {
                    return json.decodeFromString(cachedData)
                } catch (e: Exception) {
                    println("Error decoding cached data: ${e.message}")
                }
            }
        }

        return try {
            val response = client.get(apiUrl)
            val text = response.bodyAsText()

            try {
                val coursesResponse = json.decodeFromString<CoursesResponse>(text)
                storage.saveString(STORAGE_KEY_DATA, text)
                storage.saveString(STORAGE_KEY_LAST_UPDATED, coursesResponse.last_updated)
                coursesResponse
            } catch (e: Exception) {
                println("Error decoding network response: ${e.message}")
                println("Stack trace: ${e.stackTraceToString()}")
                CoursesResponse(emptyList(), "")
            }
        } catch (e: Exception) {
            println("Network error: ${e.message}")
            val cachedData = storage.getString(STORAGE_KEY_DATA)
            if (cachedData != null) {
                try {
                    json.decodeFromString(cachedData)
                } catch (e: Exception) {
                    CoursesResponse(emptyList(), "")
                }
            } else {
                CoursesResponse(emptyList(), "")
            }
        }
    }
} 