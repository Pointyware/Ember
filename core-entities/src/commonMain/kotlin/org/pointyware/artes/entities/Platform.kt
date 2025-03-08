package org.pointyware.artes.entities

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
