package com.quickness.data.repository

import com.quickness.data.request.AuthUserRequest
import com.quickness.data.response.ApiResponse
import com.quickness.data.services.AuthService
import kotlinx.serialization.json.buildJsonObject

class AuthRepositoryImpl(private val authService: AuthService) : AuthRepository {
    override fun authUser(authUserRequest: AuthUserRequest): ApiResponse {
        return try {
            authService.verifyIdToken(authUserRequest)
        } catch (e: Exception) {
            ApiResponse(
                message = "Error ${e.message}",
                status = 500,
                data = buildJsonObject {  }
            )
        }
    }
}

interface AuthRepository {
    fun authUser(authUserRequest: AuthUserRequest): ApiResponse
}