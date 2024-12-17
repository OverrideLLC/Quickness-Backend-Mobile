package com.quickness.data.repository

import com.quickness.data.request.RegisterRequest
import com.quickness.data.response.ApiResponse
import com.quickness.data.services.RegisterService
import kotlinx.serialization.json.buildJsonObject

/**
 * Implementation of [RegisterRepository] for user registration.
 *
 * This repository delegates the user registration logic to the [RegisterService].
 * @property registerService The service responsible for registering users.
 */
class RegisterRepositoryImpl(private val registerService: RegisterService) : RegisterRepository {
    /**
     * Registers a new user using the provided [RegisterRequest].
     *
     * This method uses [RegisterService.registerUser] to process the registration request.
     * If an error occurs during registration, it returns an error response with appropriate details.
     *
     * @param registerUserRequest The request containing user registration details.
     * @return An [ApiResponse] indicating the result of the registration process.
     */
    override fun registerUser(registerUserRequest: RegisterRequest): ApiResponse {
        return try {
            registerService.registerUser(registerUserRequest)
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
 * Repository interface for user registration.
 *
 * Defines the contract for implementing user registration operations.
 */
interface RegisterRepository {
    /**
     * Registers a new user using the provided [RegisterRequest].
     *
     * @param registerUserRequest The request containing user registration details.
     * @return An [ApiResponse] indicating the result of the registration process.
     */
    fun registerUser(registerUserRequest: RegisterRequest): ApiResponse
}
