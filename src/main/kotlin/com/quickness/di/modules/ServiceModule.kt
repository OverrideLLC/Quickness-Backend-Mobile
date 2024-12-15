package com.quickness.di.modules

import com.quickness.data.services.AuthService
import org.koin.dsl.module

val ServiceModule = module {
    single { AuthService(get()) }
}