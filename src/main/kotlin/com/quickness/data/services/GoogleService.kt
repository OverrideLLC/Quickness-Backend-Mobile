package com.quickness.data.services

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions

object GoogleService {
    fun initFirebaseApp(
        credentials: GoogleCredentials
    ): FirebaseApp {
        val options = FirebaseOptions.builder()
            .setProjectId("quickness-backend-7f4ac")
            .setCredentials(credentials)
            .build()

        return FirebaseApp.initializeApp(options)
            ?: throw IllegalStateException("Failed to initialize FirebaseApp")
    }

    fun initGcloud(): GoogleCredentials {
        return try {
            GoogleCredentials.getApplicationDefault()
        } catch (e: Exception) {
            throw IllegalStateException("Error obtaining Google default credentials: ${e.message}", e)
        }
    }

    fun initSecretManager(): SecretManagerServiceClient {
        return try {
            SecretManagerServiceClient.create()
        } catch (e: Exception) {
            throw IllegalStateException("Error initializing secret manager: ${e.message}", e)
        }
    }
}