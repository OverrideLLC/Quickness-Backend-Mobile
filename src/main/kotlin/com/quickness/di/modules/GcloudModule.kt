package com.quickness.di.modules

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient
import com.google.firebase.FirebaseApp
import com.quickness.data.services.GoogleService
import org.koin.dsl.module

val gcloudModule = module {
    single<FirebaseApp> { GoogleService.initFirebaseApp(get<GoogleCredentials>()) }
    single<GoogleCredentials> { GoogleService.initGcloud() }
    single<SecretManagerServiceClient> { GoogleService.initSecretManager() }
}