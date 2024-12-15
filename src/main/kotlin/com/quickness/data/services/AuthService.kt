package com.quickness.data.services

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.google.firebase.auth.FirebaseAuth
import com.quickness.data.request.AuthUserRequest
import com.quickness.data.response.ApiResponse
import com.quickness.utils.Constants
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import java.util.*

class AuthService(private val firebaseAuth: FirebaseAuth) {

    fun verifyIdToken(authUserRequest: AuthUserRequest): ApiResponse {
        return runCatching {
            val decodedToken = firebaseAuth.verifyIdToken(authUserRequest.token)
            val userId = decodedToken.uid

            val jwt = generateJwt(userId)

            ApiResponse(
                message = "Success",
                status = 200,
                data = buildJsonObject {
                    put("jwt", JsonPrimitive(jwt))
                }
            )
        }.getOrElse { exception ->
            ApiResponse(
                message = "Failure: ${exception.message}",
                status = 400,
                data = buildJsonObject { }
            )
        }
    }

    private fun generateJwt(userId: String): String {
        val algorithm = Algorithm.HMAC256(Constants.SECRET_KEY)
        val now = Date()
        return JWT.create()
            .withIssuer(Constants.ISSUER)
            .withAudience(Constants.AUDIENCE)
            .withSubject(userId)
            .withIssuedAt(now)
            .withExpiresAt(Date(now.time + Constants.EXPIRATION_TIME))
            .sign(algorithm)
    }
}