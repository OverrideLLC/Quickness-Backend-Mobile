package com.quickness.di.modules

import com.quickness.data.services.AuthService
import com.quickness.data.services.RegisterService
import org.koin.dsl.module

val ServiceModule = module {
    single { AuthService(get()) }
    single { RegisterService(get(), get()) }
}