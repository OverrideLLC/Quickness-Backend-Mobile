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

fun Application.configureAuthRouting() {
    val authRepository by inject<AuthRepository>()
    routing {
        route(Routes.AuthRoute.route) {
            post {
                try {
                    val request = call.receive<AuthUserRequest>()
                    if (request.token.isEmpty()) return@post call.respond(
                        ApiResponse(
                            message = "Error: Token is empty",
                            status = 400,
                            data = buildJsonObject {  }
                        )
                    )

                    val response = authRepository.authUser(request)

                    call.respond(response)
                } catch (e: Exception) {
                    call.respond(
                        ApiResponse(
                            message = "Error: ${e.message}, ${e.cause}",
                            status = 400,
                            data = buildJsonObject {  }
                        )
                    )
                }
            }
        }
    }
}