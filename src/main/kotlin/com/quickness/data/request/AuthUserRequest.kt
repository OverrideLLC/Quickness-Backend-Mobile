package com.quickness.data.request

import kotlinx.serialization.Serializable

/**
 * Data class representing the authentication request.
 *
 * This request is used to authenticate a user by providing an ID token.
 *
 * @property token The ID token used for user authentication.
 */
@Serializable
data class AuthUserRequest(
    val token: String
)