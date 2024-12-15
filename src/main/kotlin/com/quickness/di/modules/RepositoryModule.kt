package com.quickness.di.modules

import com.quickness.data.repository.AuthRepository
import com.quickness.data.repository.AuthRepositoryImpl
import org.koin.dsl.module

val RepositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
}