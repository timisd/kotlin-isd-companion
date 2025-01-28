package de.hshl.isd.companion.core.localization

import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.languageCode

actual fun localeLanguage(): String {
    return NSLocale.currentLocale.languageCode
} 