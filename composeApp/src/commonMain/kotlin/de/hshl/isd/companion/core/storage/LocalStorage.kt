package de.hshl.isd.companion.core.storage

import androidx.compose.runtime.staticCompositionLocalOf

val LocalStorage = staticCompositionLocalOf<Storage> { 
    error("No Storage provided") 
} 