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
            "networking" to "Networking",
            "operating_systems" to "Operating Systems",
            "computer_engineering" to "Computer Engineering",

            "binary_to_decimal" to "Binary to Decimal",
            "decimal_to_binary" to "Decimal to Binary",
            "binary_addition" to "Binary Addition",
            "twos_complement" to "Two's Complement",

            "bit_encoding" to "Bit Encoding",
            "crc" to "CRC",
            "minimal_spanning_tree" to "Minimal Spanning Tree",
            "shortest_path" to "Shortest Path",
            "netmask" to "Netmask",
            "networking_quiz" to "Networking Quiz",

            "page_replacement" to "Page Replacement",
            "buddy" to "Buddy",
            "scheduling" to "Scheduling",
            "realtime_scheduling" to "Realtime Scheduling",
            "quiz" to "Quiz",
            "os_quiz" to "Operating Systems Quiz",

            "tab_settings" to "Settings",
            "tab_courses" to "Courses",
            "tab_professors" to "Profs",
            "tab_exercises" to "Exercises",
            "tab_cafeteria" to "Cafeteria",

            "add_binary_numbers" to "Add these binary numbers:",
            "new_numbers" to "New Numbers",
            "show_solution_hint" to "Toggle the switch to see the solution",
            "go_back" to "Go back",

            "convert_binary_to_decimal" to "Convert this binary number to decimal:",
            "decimal_value" to "Decimal value",
            "new_number" to "New Number"
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
            "networking" to "Netzwerke",
            "operating_systems" to "Betriebssysteme",
            "computer_engineering" to "Technische Informatik",

            "tab_settings" to "Einstellungen",
            "tab_courses" to "Kurse",
            "tab_professors" to "Profs",
            "tab_exercises" to "Übungen",
            "tab_cafeteria" to "Mensa",

            "binary_to_decimal" to "Binär zu Dezimal",
            "decimal_to_binary" to "Dezimal zu Binär",
            "binary_addition" to "Addition binärer Zahlen",
            "twos_complement" to "Zweierkomplement",

            "bit_encoding" to "Bitcodierung",
            "crc" to "CRC",
            "minimal_spanning_tree" to "Minimaler Spannbaum",
            "shortest_path" to "Kürzester Weg",
            "netmask" to "Netzmasken",
            "networking_quiz" to "Netzwerk Quiz",

            "page_replacement" to "Seitenersetzung",
            "buddy" to "Buddy",
            "scheduling" to "Scheduling",
            "realtime_scheduling" to "Echtzeit-Scheduling",
            "quiz" to "Quiz",
            "os_quiz" to "Betriebssysteme Quiz",

            "add_binary_numbers" to "Addiere diese Binärzahlen:",
            "new_numbers" to "Neue Zahlen",
            "show_solution_hint" to "Schalter umlegen um die Lösung zu sehen",
            "go_back" to "Zurück",

            "convert_binary_to_decimal" to "Wandle diese Binärzahl in eine Dezimalzahl um:",
            "decimal_value" to "Dezimalwert",
            "new_number" to "Neue Zahl"
        )
    )

    fun get(key: String, languageCode: String): String {
        return translations[languageCode]?.get(key) ?: translations["en"]?.get(key) ?: key
    }
} 