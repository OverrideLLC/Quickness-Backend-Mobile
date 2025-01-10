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