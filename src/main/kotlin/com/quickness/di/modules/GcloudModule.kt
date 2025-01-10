package com.quickness.di.modules

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.koin.dsl.module

val gcloudModule = module {
    single {
        initFirebaseApp()
    }
}

/**
 * Initializes and returns the default [FirebaseApp].
 *
 * Ensures that the Firebase app is initialized only once during the application's lifecycle.
 * If an instance already exists, it reuses the existing app. If no instance exists,
 * a new one is initialized with the default credentials and project settings.
 *
 * @return The initialized or existing [FirebaseApp].
 * @throws IllegalStateException If default credentials cannot be obtained or if the app fails to initialize.
 */
private fun initFirebaseApp(): FirebaseApp {
    // Check if a FirebaseApp instance already exists.
    val existingApp = FirebaseApp.getApps().firstOrNull { it.name == FirebaseApp.DEFAULT_APP_NAME }

    // If an app already exists, reuse it.
    if (existingApp != null) {
        println("Using existing FirebaseApp: ${existingApp.name}")
        return existingApp
    }

    // Load Google default credentials.
    val credentials = try {
        GoogleCredentials.getApplicationDefault()
    } catch (e: Exception) {
        throw IllegalStateException("Error obtaining Google default credentials: ${e.message}", e)
    }

    // Configure FirebaseOptions with the obtained credentials and project ID.
    val options = FirebaseOptions.builder()
        .setProjectId("quickness-backend-7f4ac") // Replace with your Firebase project ID.
        .setCredentials(credentials)
        .build()

    // Initialize FirebaseApp and return it.
    return FirebaseApp.initializeApp(options)
        ?: throw IllegalStateException("Failed to initialize FirebaseApp")
}