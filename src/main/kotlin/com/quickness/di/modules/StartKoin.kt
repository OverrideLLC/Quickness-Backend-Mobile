package com.quickness.di.modules

import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

/**
 * Configures Koin for dependency injection in the Ktor application.
 *
 * This function installs Koin into the Ktor application and loads the necessary dependency injection modules.
 * It sets up logging using SLF4J for Koin and specifies the modules that should be available for
 * dependency injection: [FirebaseModule], [RepositoryModule], and [ServiceModule].
 */
fun Application.configureKoin() {
    install(Koin) {
        // Sets up SLF4J as the logging provider for Koin
        slf4jLogger()

        // Loads the dependency injection modules to be used in the application
        modules(
            FirebaseModule,   // Module for Firebase services and configuration
            RepositoryModule, // Module for repository classes
            ServiceModule,     // Module for service classes
            gcloudModule       // Module for Google Cloud services
        )
    }
}
