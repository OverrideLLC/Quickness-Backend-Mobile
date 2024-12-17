package com.quickness.routing

import com.quickness.data.repository.RegisterRepository
import com.quickness.data.request.RegisterRequest
import com.quickness.data.response.ApiResponse
import com.quickness.utils.Routes
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.buildJsonObject
import org.koin.ktor.ext.inject

/**
 * Configures the registration routing in the Ktor application.
 *
 * This function defines the route for user registration, listening to the POST request at the `/register` endpoint.
 * It uses the [RegisterRepository] to handle the logic of registering the user.
 * Upon a successful registration, the response from [RegisterRepository.registerUser] is returned.
 * If there is any failure, an appropriate error message is sent back.
 */
fun Application.configureRegisterRouting() {
    // Injects the RegisterRepository using Koin
    val registerRepository by inject<RegisterRepository>()

    routing {
        route(Routes.RegisterRoute.route) {

            /**
             * Handles POST requests for user registration.
             *
             * The request should contain a [RegisterRequest] with user details such as email, password, and phone.
             * If successful, the repository will handle the registration logic and respond with the result.
             * If an error occurs during any part of the process, an error message is returned.
             */
            post {
                runCatching {
                    // Receives the registration request from the client
                    call.receive<RegisterRequest>()
                }.onSuccess { request ->
                    runCatching {
                        // Attempts to register the user using the RegisterRepository
                        registerRepository.registerUser(request)
                    }.onSuccess { response ->
                        // Responds with the successful registration response
                        call.respond(response)
                    }.onFailure { registrationError ->
                        // If an error occurs during registration, respond with an error message
                        call.respond(
                            ApiResponse(
                                message = "Error during registration: ${registrationError.message}",
                                status = 500,
                                data = buildJsonObject { }
                            )
                        )
                    }
                }.onFailure { requestError ->
                    // If there is an issue with receiving the request, respond with an error message
                    call.respond(
                        ApiResponse(
                            message = "Error processing request: ${requestError.message}",
                            status = 500,
                            data = buildJsonObject { }
                        )
                    )
                }
            }
        }
    }
}