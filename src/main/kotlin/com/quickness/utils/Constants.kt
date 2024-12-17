package com.quickness.utils

/**
 * This object holds configuration constants for JWT authentication and other settings.
 * These constants are used to configure the JWT token creation and validation.
 * Ensure that sensitive information like SECRET_KEY is managed securely.
 */
object Constants {

    /**
     * The secret key used for signing and verifying the JWT tokens.
     * It's important to keep this secret and manage it securely in a production environment.
     */
    internal const val SECRET_KEY = "UekMZSrPVBq3rKVGAcVA0qexC1ycLNpZUBDA9gYGmLw="

    /**
     * The issuer of the JWT token.
     * This should be a unique string that identifies the entity issuing the token.
     */
    internal const val ISSUER = "Quickness"

    /**
     * The audience for the JWT token.
     * This specifies the intended recipient(s) of the token.
     * For example, this could be "user-mobile" for a mobile app client.
     */
    internal const val AUDIENCE = "user-mobile"

    /**
     * The expiration time of the JWT token.
     * This value is in milliseconds (30 days in this case).
     * After this time, the token will no longer be valid.
     */
    internal const val EXPIRATION_TIME = 2_592_000_000L // 30 days in milliseconds
}
