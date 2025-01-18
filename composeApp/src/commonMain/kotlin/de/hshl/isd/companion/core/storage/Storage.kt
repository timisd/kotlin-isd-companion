package de.hshl.isd.companion.core.storage

expect class Storage {
    suspend fun saveString(key: String, value: String)
    suspend fun getString(key: String): String?
}

expect fun createStorage(): Storage 