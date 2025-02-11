package de.hshl.isd.companion.core.platform

enum class PlatformType {
    ANDROID,
    IOS
}

interface Platform {
    val name: String
    val type: PlatformType
}

expect fun getPlatform(): Platform