package com.quickness.di.modules

import com.google.firebase.auth.FirebaseAuth
import com.quickness.data.services.AuthService
import com.quickness.data.services.JwtService
import com.quickness.data.services.RegisterService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

/**
 * Dependency Injection (DI) module for service classes.
 *
 * This module provides singleton instances of services that manage the business logic of the application.
 * The services interact with repositories and other components to perform tasks such as user authentication,
 * registration, and data handling.
 */
val ServiceModule = module {

    /**
     * Provides a singleton instance of [AuthService].
     *
     * This service is responsible for managing user authentication-related operations, such as verifying
     * tokens and generating JWT tokens for authenticated users.
     */
    single { AuthService(get<FirebaseAuth>(), get<JwtService>()) }

    /**
     * Provides a singleton instance of [RegisterService].
     *
     * This service handles user registration tasks, including creating new users, storing their data,
     * and interacting with Firebase and Firestore.
     */
    single { RegisterService(get(), get()) }

    singleOf(::JwtService)
}
