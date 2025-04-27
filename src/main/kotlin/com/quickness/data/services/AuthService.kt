package com.quickness.data.services

import com.google.firebase.auth.FirebaseAuth
import com.quickness.data.request.AuthUserRequest
import com.quickness.data.response.ApiResponse
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject

/**
 * Servicio para la autenticación de usuarios mediante Firebase y la generación de JWT.
 * Este servicio se encarga de verificar el token de identificación de Firebase y generar un JWT
 * basado en la información del usuario autenticado.
 *
 * @param firebaseAuth Instancia de [FirebaseAuth] que se utiliza para la verificación del token.
 */
class AuthService(
    private val firebaseAuth: FirebaseAuth,
    private val jwtService: JwtService
) {

    /**
     * Verifica el token de identificación proporcionado por el usuario y genera un JWT
     * si la verificación es exitosa.
     *
     * @param authUserRequest Objeto que contiene el token de autenticación a ser verificado.
     * @return [ApiResponse] con el resultado de la operación, incluyendo el JWT si la verificación es exitosa.
     */
    fun verifyIdToken(authUserRequest: AuthUserRequest): ApiResponse {
        return runCatching {
            // Verificar el token de Firebase y obtener el UID del usuario
            val decodedToken = firebaseAuth.verifyIdToken(authUserRequest.token)
            val userId = decodedToken.uid

            // Generar un JWT utilizando el UID del usuario
            val jwt = jwtService.generateJwt(userId)

            // Retornar respuesta con el JWT
            ApiResponse(
                message = "Success",
                status = 200,
                data = buildJsonObject {
                    put("jwt", JsonPrimitive(jwt))
                }
            )
        }.getOrElse { exception ->
            // En caso de error, devolver una respuesta de fallo con el mensaje del error
            ApiResponse(
                message = "Failure: ${exception.message}",
                status = 400,
                data = buildJsonObject { }
            )
        }
    }
}