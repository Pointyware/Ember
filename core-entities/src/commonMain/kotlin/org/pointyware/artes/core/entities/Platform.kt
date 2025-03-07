package org.pointyware.artes.core.entities

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform