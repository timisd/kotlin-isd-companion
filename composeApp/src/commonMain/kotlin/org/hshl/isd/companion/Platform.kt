package org.hshl.isd.companion

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform