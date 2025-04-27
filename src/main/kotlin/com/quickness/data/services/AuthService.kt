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

/**
 * Servicio para la autenticación de usuarios mediante Firebase y la generación de JWT.
 * Este servicio se encarga de verificar el token de identificación de Firebase y generar un JWT
 * basado en la información del usuario autenticado.
 *
 * @param firebaseAuth Instancia de [FirebaseAuth] que se utiliza para la verificación del token.
 */
class AuthService(private val firebaseAuth: FirebaseAuth) {

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
            val jwt = generateJwt(userId)

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

    /**
     * Genera un JWT (JSON Web Token) utilizando el [userId] del usuario.
     *
     * @param userId El identificador único del usuario autenticado.
     * @return El JWT generado como una cadena.
     */
    private fun generateJwt(userId: String): String {
        // Definir el algoritmo para firmar el JWT (HMAC con una clave secreta)
        val algorithm = Algorithm.HMAC256(Constants.SECRET_KEY)

        // Definir la fecha y hora actual para la emisión del token
        val now = Date()

        // Crear y firmar el JWT
        return JWT.create()
            .withIssuer(Constants.ISSUER)  // Emisor del JWT
            .withAudience(Constants.AUDIENCE)  // Público objetivo del JWT
            .withSubject(userId)  // ID del usuario como sujeto del token
            .withIssuedAt(now)  // Fecha y hora de emisión
            .withExpiresAt(Date(now.time + Constants.EXPIRATION_TIME))  // Fecha de expiración del token
            .sign(algorithm)  // Firmar el JWT con el algoritmo
    }
}
