package de.hshl.isd.companion.features.professors.api

import de.hshl.isd.companion.core.storage.Storage
import de.hshl.isd.companion.features.professors.model.ProfessorsResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json

class ProfessorsApi(private val storage: Storage) {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }
    
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(json)
        }
    }
    
    private val apiUrl = "https://raw.githubusercontent.com/timisd/kotlin-isd-companion/main/resources/professors.json"
    private val STORAGE_KEY_DATA = "professors_data"
    private val STORAGE_KEY_LAST_UPDATED = "professors_last_updated"
    
    suspend fun getProfessors(forceRefresh: Boolean = false): ProfessorsResponse {
        // Try to get cached data first
        if (!forceRefresh) {
            val cachedData = storage.getString(STORAGE_KEY_DATA)
            if (cachedData != null) {
                return json.decodeFromString(cachedData)
            }
        }

        return try {
            val response = client.get(apiUrl)
            val text = response.bodyAsText()
            val professorsResponse = json.decodeFromString<ProfessorsResponse>(text)
            
            // Check if the new data is newer than our cached data
            val lastUpdated = storage.getString(STORAGE_KEY_LAST_UPDATED)
            if (lastUpdated == null || professorsResponse.last_updated > lastUpdated) {
                storage.saveString(STORAGE_KEY_DATA, text)
                storage.saveString(STORAGE_KEY_LAST_UPDATED, professorsResponse.last_updated)
            }
            
            professorsResponse
        } catch (e: Exception) {
            // If network request fails, try to get cached data
            val cachedData = storage.getString(STORAGE_KEY_DATA)
            if (cachedData != null) {
                json.decodeFromString(cachedData)
            } else {
                ProfessorsResponse(emptyList(), "")
            }
        }
    }
} 