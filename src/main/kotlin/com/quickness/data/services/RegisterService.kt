package com.quickness.data.services

import com.google.cloud.firestore.Firestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserRecord
import com.quickness.data.request.RegisterRequest
import com.quickness.data.response.ApiResponse
import kotlinx.serialization.json.buildJsonObject

/**
 * Service responsible for registering users in the system.
 *
 * @property firebaseAuth Firebase Authentication instance to manage user creation.
 * @property firestore Firestore instance for database interactions.
 */
class RegisterService(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: Firestore,
) {
    /**
     * Registers a new user based on the provided [RegisterRequest].
     *
     * This function performs the following steps:
     * 1. Creates a new Firebase user.
     * 2. Stores additional user data in the Firestore database.
     *
     * If any error occurs during the process, an appropriate error response is returned.
     *
     * @param registerUserRequest The request containing user registration details.
     * @return An [ApiResponse] indicating the success or failure of the registration process.
     */
    fun registerUser(registerUserRequest: RegisterRequest): ApiResponse {
        return try {
            // Step 1: Create a new Firebase user.
            val userRecordRequest = UserRecord.CreateRequest()
                .setEmail(registerUserRequest.email)
                .setPassword(registerUserRequest.password)
                .setPhoneNumber(registerUserRequest.phone)

            val userRecord = runCatching {
                firebaseAuth.createUser(userRecordRequest)
            }.getOrElse { exception ->
                return ApiResponse(
                    message = "Error creating user: ${exception.message}",
                    status = 500,
                    data = buildJsonObject { }
                )
            }

            // Step 2: Add user data to Firestore.
            val userData = mapOf(
                "name" to registerUserRequest.name,
                "curp" to registerUserRequest.curp,
                "balance" to 0.0,
                "id_token" to "TOKENID",
                "id_token_cards" to "TOKENID",
                "credits_cards" to 0
            )

            runCatching {
                firestore.collection("Users").document(userRecord.uid).set(userData).get()
            }.onSuccess {
                return ApiResponse(
                    message = "User registered successfully",
                    status = 200,
                    data = buildJsonObject { }
                )
            }.onFailure { firestoreException ->
                return ApiResponse(
                    message = "Error saving user data: ${firestoreException.message}",
                    status = 500,
                    data = buildJsonObject { }
                )
            }

            // Default response if something goes wrong.
            ApiResponse(
                message = "Unexpected error occurred",
                status = 500,
                data = buildJsonObject { }
            )
        } catch (e: Exception) {
            // General exception handling.
            ApiResponse(
                message = "Error: ${e.message}",
                status = 500,
                data = buildJsonObject { }
            )
        }
    }
}
