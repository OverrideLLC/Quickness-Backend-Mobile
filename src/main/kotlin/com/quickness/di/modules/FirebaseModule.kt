package com.quickness.di.modules

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.cloud.FirestoreClient
import org.koin.dsl.module

/**
 * Dependency Injection (DI) module for Firebase services.
 *
 * This module provides singleton instances of [FirebaseAuth] and [Firestore],
 * ensuring that Firebase is properly initialized and reused across the application.
 *
 * The module guarantees that the FirebaseApp is only initialized once, and the
 * instances of [FirebaseAuth] and [Firestore] are safely retrieved.
 */
val FirebaseModule = module {

    /**
     * Provides a singleton instance of [FirebaseAuth].
     *
     * This method initializes FirebaseApp and returns a [FirebaseAuth] instance
     * for managing user authentication.
     */
    single<FirebaseAuth> {
        FirebaseAuth.getInstance(initFirebaseApp())
    }

    /**
     * Provides a singleton instance of [Firestore].
     *
     * This method initializes FirebaseApp and returns a [Firestore] instance
     * for interacting with the Firestore database.
     */
    single<Firestore> {
        FirestoreClient.getFirestore(initFirebaseApp())
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