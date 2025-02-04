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
            "new_number" to "New Number",

            "convert_decimal_to_binary" to "Convert this decimal number to binary:",
            "binary_value" to "Binary value",

            "convert_to_twos_complement" to "Convert this number to two's complement:",
            "original_binary" to "Original binary",
            "inverted_binary" to "Inverted binary",
            "twos_complement_result" to "Two's complement",
            "step_by_step" to "Step by step",
            "step_1" to "Step 1: Write the binary number",
            "step_2" to "Step 2: Invert all bits",
            "step_3" to "Step 3: Add 1 to the result",

            "encode_binary_sequence" to "Encode this binary sequence:",
            "binary_sequence" to "Binary sequence",
            "nrz_encoding" to "NRZ (±)",
            "nrz_i_encoding" to "NRZ-I (±0)",
            "mlt_3_encoding" to "MLT-3 (+0-)",
            "new_sequence" to "New Sequence",
            "generate_new_tree" to "New Tree",
            "show_solution_mst" to "Show Solution",
            "mst_exercise_description" to "Find the minimal spanning tree:",
            "mst_solution_hint" to "Toggle the switch to see the solution",
            "calculate_network_host" to "Calculate network and host addresses:",
            "ip_address" to "IP Address",
            "network_address" to "Network Address",
            "host_address" to "Host Address",
            "new_ip" to "New IP",
            "show_solution_netmask" to "Toggle the switch to see the solution",
            "find_shortest_path" to "Find the shortest path:",
            "start_node" to "Start node",
            "end_node" to "End node",
            "new_graph" to "New Graph",
            "show_solution_shortest_path" to "Toggle the switch to see the solution",
            "shortest_path_length" to "Path length",
            "buddy_exercise_description" to "Allocate memory blocks using the Buddy system:",
            "memory_size" to "Memory Size",
            "request_size" to "Request Size",
            "allocate" to "Allocate",
            "deallocate" to "Deallocate",
            "reset" to "Reset",
            "memory_blocks" to "Memory Blocks",
            "show_solution_buddy" to "Toggle the switch to see the solution"
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
            "new_number" to "Neue Zahl",

            "convert_decimal_to_binary" to "Wandle diese Dezimalzahl in eine Binärzahl um:",
            "binary_value" to "Binärwert",

            "convert_to_twos_complement" to "Wandle diese Zahl in das Zweierkomplement um:",
            "original_binary" to "Ursprüngliche Binärzahl",
            "inverted_binary" to "Invertierte Binärzahl",
            "twos_complement_result" to "Zweierkomplement",
            "step_by_step" to "Schritt für Schritt",
            "step_1" to "Schritt 1: Schreibe die Binärzahl",
            "step_2" to "Schritt 2: Invertiere alle Bits",
            "step_3" to "Schritt 3: Addiere 1 zum Ergebnis",

            "encode_binary_sequence" to "Codiere diese Binärsequenz:",
            "binary_sequence" to "Binärsequenz",
            "nrz_encoding" to "NRZ (±)",
            "nrz_i_encoding" to "NRZ-I (±0)",
            "mlt_3_encoding" to "MLT-3 (+0-)",
            "new_sequence" to "Neue Sequenz",
            "generate_new_tree" to "Neuer Baum",
            "show_solution_mst" to "Lösung anzeigen",
            "mst_exercise_description" to "Finde den minimalen Spannbaum:",
            "mst_solution_hint" to "Schalter umlegen um die Lösung zu sehen",
            "calculate_network_host" to "Berechne Netzwerk- und Host-Adressen:",
            "ip_address" to "IP-Adresse",
            "network_address" to "Netzwerkadresse",
            "host_address" to "Host-Adresse",
            "new_ip" to "Neue IP",
            "show_solution_netmask" to "Schalter umlegen um die Lösung zu sehen",
            "find_shortest_path" to "Finde den kürzesten Weg:",
            "start_node" to "Startknoten",
            "end_node" to "Zielknoten",
            "new_graph" to "Neuer Graph",
            "show_solution_shortest_path" to "Schalter umlegen um die Lösung zu sehen",
            "shortest_path_length" to "Weglänge",
            "buddy_exercise_description" to "Speicherblöcke mit dem Buddy-System zuweisen:",
            "memory_size" to "Speichergröße",
            "request_size" to "Angeforderte Größe",
            "allocate" to "Zuweisen",
            "deallocate" to "Freigeben",
            "reset" to "Zurücksetzen",
            "memory_blocks" to "Speicherblöcke",
            "show_solution_buddy" to "Schalter umlegen um die Lösung zu sehen"
        )
    )

    fun get(key: String, languageCode: String): String {
        return translations[languageCode]?.get(key) ?: translations["en"]?.get(key) ?: key
    }
} 