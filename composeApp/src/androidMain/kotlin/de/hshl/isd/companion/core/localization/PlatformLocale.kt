package de.hshl.isd.companion.core.localization

import java.util.Locale

actual fun localeLanguage(): String {
    return Locale.getDefault().language
} 