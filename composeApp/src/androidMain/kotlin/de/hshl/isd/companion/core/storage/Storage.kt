package de.hshl.isd.companion.core.storage

import android.content.Context
import android.content.SharedPreferences
import de.hshl.isd.companion.MainApplication

actual class Storage(private val context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("professors_data", Context.MODE_PRIVATE)

    actual suspend fun saveString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    actual suspend fun getString(key: String): String? {
        return prefs.getString(key, null)
    }
}

actual fun createStorage(): Storage {
    return Storage(MainApplication.INSTANCE)
} 