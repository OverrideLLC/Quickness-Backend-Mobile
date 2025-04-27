package com.quickness.data.services

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.quickness.data.repository.GoogleRepository
import com.quickness.utils.Constants
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*

class JwtService(
    private val googleRepository: GoogleRepository
) {
    private fun getPublicKey(): RSAPublicKey {
        val publicKeyPEM = googleRepository.getSecret("projects/322358291466/secrets/public_key_rs256_jwt/versions/1")
            ?.replace("-----BEGIN PUBLIC KEY-----", "")
            ?.replace("-----END PUBLIC KEY-----", "")
            ?.replace("\\s".toRegex(), "")

        if (publicKeyPEM.isNullOrBlank()) throw RuntimeException("Public key not found")

        val keyBytes = Base64.getDecoder().decode(publicKeyPEM)
        val keySpec = X509EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePublic(keySpec) as RSAPublicKey
    }

    private fun getPrivateKey(): RSAPrivateKey {
        val privateKeyPEM = googleRepository.getSecret("projects/322358291466/secrets/private_key_rs256_jwt/versions/1")
            ?.replace("-----BEGIN PRIVATE KEY-----", "")
            ?.replace("-----END PRIVATE KEY-----", "")
            ?.replace("\\s".toRegex(), "")

        if (privateKeyPEM.isNullOrBlank()) throw RuntimeException("Public key not found")

        val keyBytes = Base64.getDecoder().decode(privateKeyPEM)
        val keySpec = PKCS8EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePrivate(keySpec) as RSAPrivateKey
    }

    fun generateJwt(uid: String): String {
        val privateKey = getPrivateKey()
        val algorithm = Algorithm.RSA256(null, privateKey) // Firma con la clave privada

        val now = Date()

        return JWT.create()
            .withIssuer(Constants.ISSUER)
            .withAudience(Constants.AUDIENCE)
            .withSubject(uid)
            .withClaim(
                "claims", mapOf(
                    "device" to "a663f453486645cda2cea6f868cab7",
                    "role" to Constants.ROLE
                )
            )
            .withIssuedAt(now)
            .withIssuedAt(now)
            .withExpiresAt(Date(now.time + Constants.EXPIRATION_TIME))
            .sign(algorithm)
    }

    fun verifyJwt(token: String): Boolean {
        return try {
            val publicKey = getPublicKey()
            val algorithm = Algorithm.RSA256(publicKey, null) // Verificación con clave pública

            val verifier = JWT.require(algorithm)
                .withIssuer(Constants.ISSUER)
                .build()

            verifier.verify(token) // Verifica el token
            true
        } catch (e: Exception) {
            println("Error verificando el token: ${e.message}")
            false
        }
    }
}