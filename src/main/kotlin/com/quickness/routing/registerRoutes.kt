package com.quickness.routing

import com.quickness.data.repository.RegisterRepository
import com.quickness.data.repository.RegisterRepositoryImpl
import com.quickness.data.request.RegisterRequest
import com.quickness.data.response.ApiResponse
import com.quickness.utils.Routes
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.buildJsonObject
import org.koin.ktor.ext.inject

fun Application.configureRegisterRouting() {
    val registerRepository by inject<RegisterRepository>()
    routing {
        route(Routes.RegisterRoute.route) {
            post {
                runCatching {
                    call.receive<RegisterRequest>()
                }.onSuccess { request ->
                    runCatching {
                        registerRepository.registerUser(request)
                    }.onSuccess { response ->
                        call.respond(response)
                    }.onFailure {
                        call.respond(
                            ApiResponse(
                                message = "Error: ${it.message}",
                                status = 500,
                                data = buildJsonObject { }
                            )
                        )
                    }
                }.onFailure {
                    call.respond(
                        ApiResponse(
                            message = "Error: ${it.message}",
                            status = 500,
                            data = buildJsonObject { }
                        )
                    )
                }
            }
        }
    }
}

