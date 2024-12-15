package com.quickness.di.modules

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import org.koin.dsl.module

val FirebaseModule = module {
    single<FirebaseAuth> {
        initFirebaseApp().also { println("FirebaseApp initialized: ${it.name}") }
        FirebaseAuth.getInstance().also { println("FirebaseAuth instance acquired.") }
    }
}

private fun initFirebaseApp(): FirebaseApp {
    // Verificar si ya existe una instancia
    val existingApp = FirebaseApp.getApps().firstOrNull { it.name == FirebaseApp.DEFAULT_APP_NAME }
    if (existingApp != null) {
        println("Using existing FirebaseApp: ${existingApp.name}")
        return existingApp
    }

    // Obtener las credenciales predeterminadas de Google
    val credentials = try {
        GoogleCredentials.getApplicationDefault()  // Obtiene las credenciales predeterminadas
    } catch (e: Exception) {
        throw IllegalStateException("Error obtaining Google default credentials: ${e.message}", e)
    }

    // Configurar las opciones de Firebase
    val options = FirebaseOptions.builder()
        .setProjectId("quickness-backend-7f4ac")
        .setCredentials(credentials)
        .build()

    // Inicializar la app de Firebase
    return FirebaseApp.initializeApp(options)
        ?: throw IllegalStateException("Failed to initialize FirebaseApp")
}