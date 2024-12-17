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
 * This service handles the process of user registration by first creating a new user in Firebase Authentication,
 * then storing additional user details in Firestore.
 *
 * @property firebaseAuth The Firebase Authentication instance used to create and manage users.
 * @property firestore The Firestore instance used to store user-related data.
 */
class RegisterService(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: Firestore
) {

    /**
     * Registers a new user based on the provided [RegisterRequest].
     *
     * This method performs the following steps:
     * 1. Creates a new Firebase user with the provided email and password.
     * 2. Stores the user's additional data (name, CURP, balance, etc.) in Firestore.
     *
     * If an error occurs during the process, the function returns an [ApiResponse] with the appropriate error message.
     *
     * @param registerUserRequest The request containing the details of the user to be registered.
     * @return An [ApiResponse] indicating the success or failure of the registration process.
     */
    fun registerUser(registerUserRequest: RegisterRequest): ApiResponse {
        return try {
            // Step 1: Create a new Firebase user
            val userRecordRequest = UserRecord.CreateRequest()
                .setEmail(registerUserRequest.email)
                .setPassword(registerUserRequest.password)
                .setPhoneNumber(registerUserRequest.phone)

            // Attempt to create the Firebase user, and handle any exceptions that may occur.
            val userRecord = runCatching {
                firebaseAuth.createUser(userRecordRequest)
            }.getOrElse { exception ->
                return ApiResponse(
                    message = "Error creating user: ${exception.message}",
                    status = 500,
                    data = buildJsonObject { }
                )
            }

            // Step 2: Add user data to Firestore
            val userData = mapOf(
                "name" to registerUserRequest.name,
                "curp" to registerUserRequest.curp,
                "balance" to 0.0,  // Default balance
                "id_token" to "TOKENID",  // Placeholder for id_token
                "id_token_cards" to "TOKENID",  // Placeholder for id_token_cards
                "credits_cards" to 0  // Default credits
            )

            // Attempt to store the user data in Firestore, handling any errors.
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

            // If we reach this point, something went wrong, and a generic error response is returned.
            ApiResponse(
                message = "Unexpected error occurred",
                status = 500,
                data = buildJsonObject { }
            )
        } catch (e: Exception) {
            // General exception handling: return a response with the error message.
            ApiResponse(
                message = "Error: ${e.message}",
                status = 500,
                data = buildJsonObject { }
            )
        }
    }
}
