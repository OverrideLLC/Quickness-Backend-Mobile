package com.quickness.data.repository

import com.quickness.data.request.AuthUserRequest
import com.quickness.data.response.ApiResponse
import com.quickness.data.services.AuthService
import kotlinx.serialization.json.buildJsonObject

/**
 * Implementation of [AuthRepository] for user authentication.
 *
 * This repository delegates the authentication logic to the [AuthService].
 * @property authService The service responsible for verifying user ID tokens.
 */
class AuthRepositoryImpl(private val authService: AuthService) : AuthRepository {
    /**
     * Authenticates a user by verifying their ID token.
     *
     * This method uses [AuthService.verifyIdToken] to validate the provided ID token.
     * If an error occurs during authentication, it returns an error response with appropriate details.
     *
     * @param authUserRequest The request containing the user's authentication details.
     * @return An [ApiResponse] indicating the result of the authentication process.
     */
    override fun authUser(authUserRequest: AuthUserRequest): ApiResponse {
        return try {
            authService.verifyIdToken(authUserRequest)
        } catch (e: Exception) {
            ApiResponse(
                message = "Error: ${e.message}",
                status = 500,
                data = buildJsonObject { }
            )
        }
    }
}

/**
 * Repository interface for user authentication.
 *
 * Defines the contract for implementing authentication operations.
 */
interface AuthRepository {
    /**
     * Authenticates a user by verifying their ID token.
     *
     * @param authUserRequest The request containing the user's authentication details.
     * @return An [ApiResponse] indicating the result of the authentication process.
     */
    fun authUser(authUserRequest: AuthUserRequest): ApiResponse
}