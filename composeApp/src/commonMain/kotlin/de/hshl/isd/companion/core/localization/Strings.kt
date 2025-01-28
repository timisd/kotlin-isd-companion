package de.hshl.isd.companion.core.localization

object Strings {
    data class Language(
        val code: String,
        val displayName: String
    )

    val supportedLanguages = listOf(
        Language("en", "English"),
        Language("de", "Deutsch")
    )

    private val translations = mapOf(
        "en" to mapOf(
            "settings" to "Settings",
            "dark_mode" to "Dark Mode",
            "language" to "Language",
            "courses" to "Courses",
            "professors" to "Professors",
            "exercises" to "Exercises",
            "cafeteria" to "Cafeteria",
            "schedule" to "Schedule",
            "refresh" to "Refresh",
            "retry" to "Retry",
            "menu_for" to "Menu for",
            "no_menu_available" to "No menu available for this date",
            "search" to "Search...",
            "mail" to "Mail",
            "phone" to "Phone",
            "cancel" to "Cancel",
            "retry" to "Retry",
            "monday" to "Monday",
            "tuesday" to "Tuesday",
            "wednesday" to "Wednesday",
            "thursday" to "Thursday",
            "friday" to "Friday",

            "tab_settings" to "Settings",
            "tab_courses" to "Courses",
            "tab_professors" to "Profs",
            "tab_exercises" to "Exercises",
            "tab_cafeteria" to "Cafeteria"
        ),
        "de" to mapOf(
            "settings" to "Einstellungen",
            "dark_mode" to "Dunkelmodus",
            "language" to "Sprache",
            "courses" to "Kurse",
            "professors" to "Professoren",
            "exercises" to "Übungen",
            "cafeteria" to "Mensa",
            "schedule" to "Stundenplan",
            "refresh" to "Aktualisieren",
            "retry" to "Wiederholen",
            "menu_for" to "Menü für",
            "no_menu_available" to "Kein Menü verfügbar für dieses Datum",
            "search" to "Suchen...",
            "mail" to "E-Mail",
            "phone" to "Telefon",
            "cancel" to "Abbrechen",
            "retry" to "Wiederholen",
            "monday" to "Montag",
            "tuesday" to "Dienstag",
            "wednesday" to "Mittwoch",
            "thursday" to "Donnerstag",
            "friday" to "Freitag",

            "tab_settings" to "Einstellungen",
            "tab_courses" to "Kurse",
            "tab_professors" to "Profs",
            "tab_exercises" to "Übungen",
            "tab_cafeteria" to "Mensa"
        )
    )

    fun get(key: String, languageCode: String): String {
        return translations[languageCode]?.get(key) ?: translations["en"]?.get(key) ?: key
    }
} 