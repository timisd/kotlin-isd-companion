package de.hshl.isd.companion.core.storage

import platform.Foundation.NSUserDefaults

actual class Storage {
    private val defaults = NSUserDefaults.standardUserDefaults

    actual suspend fun saveString(key: String, value: String) {
        defaults.setObject(value, key)
    }

    actual suspend fun getString(key: String): String? {
        return defaults.stringForKey(key)
    }
}

actual fun createStorage(): Storage {
    return Storage()
} 