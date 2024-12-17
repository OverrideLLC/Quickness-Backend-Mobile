package com.quickness.application

import com.quickness.routing.configureAuthRouting
import com.quickness.routing.configureRegisterRouting
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*

/**
 * Configures the serialization settings for the application.
 *
 * This function installs the ContentNegotiation plugin and configures it to use
 * Kotlinx Serialization for JSON content. This ensures that all incoming and outgoing
 * data in the application is automatically serialized and deserialized in JSON format.
 */
fun Application.configureSerialization() {
    // Install ContentNegotiation with JSON serialization
    install(ContentNegotiation) {
        json()  // Default configuration for Kotlinx Serialization JSON
    }

    // Configure routing for the application
    configureRoutings()
}

/**
 * Configures the routing for the application, including authentication and registration.
 * You can add more routes here as your application grows.
 */
fun Application.configureRoutings() {
    routing {
        // Configure routes related to user authentication
        configureAuthRouting()

        // Configure routes related to user registration
        configureRegisterRouting()

        // Additional routes can be added here as needed.
    }
}
