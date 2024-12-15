package com.quickness.application

import com.quickness.di.modules.configureKoin
import io.ktor.server.application.*
import io.ktor.server.cio.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureKoin()
    configureSerialization()
    configureRouting()
}