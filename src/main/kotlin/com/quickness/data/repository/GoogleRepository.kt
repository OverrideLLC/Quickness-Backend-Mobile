package com.quickness.data.repository

import com.google.cloud.secretmanager.v1.AccessSecretVersionResponse
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient

class GoogleRepositoryImpl(
    private val secretManager: SecretManagerServiceClient,
): GoogleRepository {
    override fun getSecret(secretId: String): String? {
        val secret: AccessSecretVersionResponse = secretManager.accessSecretVersion(secretId)
        return secret.payload.data.toStringUtf8()
    }
}

interface GoogleRepository {
    fun getSecret(secretName: String): String?
}