package com.quickness.di.modules

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.cloud.FirestoreClient
import org.koin.dsl.module

/**
 * Dependency injection module for Firebase services.
 *
 * Provides single instances of [FirebaseAuth] and [Firestore], ensuring proper initialization of FirebaseApp.
 */
val FirebaseModule = module {
    /**
     * Provides a singleton instance of [FirebaseAuth].
     */
    single<FirebaseAuth> {
        FirebaseAuth.getInstance(initFirebaseApp())
    }

    /**
     * Provides a singleton instance of [Firestore].
     */
    single<Firestore> {
        FirestoreClient.getFirestore(initFirebaseApp())
    }
}

/**
 * Initializes and returns the default [FirebaseApp].
 *
 * Ensures that a Firebase app is initialized only once during the application lifecycle. If an instance
 * already exists, it reuses the existing app. Otherwise, it initializes a new instance with default credentials
 * and project settings.
 *
 * @return The initialized or existing [FirebaseApp].
 * @throws IllegalStateException If default credentials cannot be obtained or the app fails to initialize.
 */
private fun initFirebaseApp(): FirebaseApp {
    // Check if a FirebaseApp instance already exists.
    val existingApp = FirebaseApp.getApps().firstOrNull { it.name == FirebaseApp.DEFAULT_APP_NAME }
    if (existingApp != null) {
        println("Using existing FirebaseApp: ${existingApp.name}")
        return existingApp
    }

    // Load default Google credentials.
    val credentials = try {
        GoogleCredentials.getApplicationDefault()
    } catch (e: Exception) {
        throw IllegalStateException("Error obtaining Google default credentials: ${e.message}", e)
    }

    // Configure Firebase options.
    val options = FirebaseOptions.builder()
        .setProjectId("quickness-backend-7f4ac")
        .setCredentials(credentials)
        .build()

    // Initialize the FirebaseApp.
    return FirebaseApp.initializeApp(options)
        ?: throw IllegalStateException("Failed to initialize FirebaseApp")
}
