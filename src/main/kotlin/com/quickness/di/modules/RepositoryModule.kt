package com.quickness.di.modules

import com.quickness.data.repository.AuthRepository
import com.quickness.data.repository.AuthRepositoryImpl
import com.quickness.data.repository.RegisterRepository
import com.quickness.data.repository.RegisterRepositoryImpl
import org.koin.dsl.module

val RepositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<RegisterRepository> { RegisterRepositoryImpl(get()) }
}