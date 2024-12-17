package com.quickness.data.repository

import com.quickness.data.request.RegisterRequest
import com.quickness.data.response.ApiResponse
import com.quickness.data.services.RegisterService
import kotlinx.serialization.json.buildJsonObject

class RegisterRepositoryImpl(private val registerService: RegisterService) : RegisterRepository {
    override fun registerUser(registerUserRequest: RegisterRequest): ApiResponse {
        return try {
            registerService.registerUser(registerUserRequest)
        } catch (e: Exception) {
            ApiResponse(
                message = "Error ${e.message}",
                status = 500,
                data = buildJsonObject { }
            )
        }
    }
}

interface RegisterRepository {
    fun registerUser(registerUserRequest: RegisterRequest): ApiResponse
}