package com.quickness.di.modules

import com.quickness.data.repository.AuthRepository
import com.quickness.data.repository.AuthRepositoryImpl
import com.quickness.data.repository.GoogleRepository
import com.quickness.data.repository.GoogleRepositoryImpl
import com.quickness.data.repository.RegisterRepository
import com.quickness.data.repository.RegisterRepositoryImpl
import org.koin.dsl.module

/**
 * Dependency Injection (DI) module for repository classes.
 *
 * This module provides singleton instances of repositories, which are responsible
 * for handling the data layer of the application. The repositories interact with
 * external services like Firebase or Firestore and provide the necessary data to
 * the rest of the application.
 */
val RepositoryModule = module {

    /**
     * Provides a singleton instance of [AuthRepository].
     *
     * This repository handles user authentication-related operations, including
     * login, token verification, and managing user sessions.
     */
    single<AuthRepository> { AuthRepositoryImpl(get()) }

    /**
     * Provides a singleton instance of [RegisterRepository].
     *
     * This repository is responsible for handling user registration, including
     * creating new users, storing their data, and interacting with external services.
     */
    single<RegisterRepository> { RegisterRepositoryImpl(get()) }

    single<GoogleRepository> { GoogleRepositoryImpl(get()) }
}
