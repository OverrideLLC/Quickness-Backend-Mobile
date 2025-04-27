package com.quickness.application

import com.quickness.di.modules.configureKoin
import io.ktor.server.application.*
import io.ktor.server.cio.*

/**
 * Main entry point for the application.
 * Starts the Ktor server with the provided arguments.
 */
fun main(args: Array<String>) {
    EngineMain.main(args) // Inicia el servidor con el motor CIO (Ktor Engine)
}

/**
 * Application module configuration.
 * Sets up dependencies, routing, and other server settings.
 */
fun Application.module() {
    // Configure Koin for dependency injection
    configureKoin()

    // Configure serialization (JSON, etc.)
    configureSerialization()

    // Setup routes for the API
    configureRouting()
}
