package com.quickness.routing

import com.quickness.data.repository.AuthRepository
import com.quickness.data.request.AuthUserRequest
import com.quickness.data.response.ApiResponse
import com.quickness.utils.Routes
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.buildJsonObject
import org.koin.ktor.ext.inject

/**
 * Configures the authentication routing in the Ktor application.
 *
 * This function defines the routes related to user authentication. It listens to the `/auth` endpoint
 * and handles POST requests for user authentication. The `AuthRepository` is used to authenticate the user
 * based on the provided token.
 */
fun Application.configureAuthRouting() {
    // Injects the AuthRepository using Koin
    val authRepository by inject<AuthRepository>()

    routing {
        route(Routes.AuthRoute.route) {

            /**
             * Handles POST requests for user authentication.
             *
             * The request should contain a token to authenticate the user. If the token is valid, the
             * response from [authRepository.authUser] is returned. If the token is empty or invalid,
             * an appropriate error message is sent back.
             */
            post {
                try {
                    // Receives the authentication request from the client
                    val request = call.receive<AuthUserRequest>()

                    // Check if the token is empty and return an error response
                    if (request.token.isEmpty()) {
                        return@post call.respond(
                            ApiResponse(
                                message = "Error: Token is empty",
                                status = 400,
                                data = buildJsonObject { }
                            )
                        )
                    }

                    // Calls the repository to authenticate the user and get the response
                    val response = authRepository.authUser(request)

                    // Responds with the authentication result
                    call.respond(response)

                } catch (e: Exception) {
                    // Catches any unexpected exceptions and returns an error response
                    call.respond(
                        ApiResponse(
                            message = "Error: ${e.message}, ${e.cause}",
                            status = 400,
                            data = buildJsonObject { }
                        )
                    )
                }
            }
        }
    }
}