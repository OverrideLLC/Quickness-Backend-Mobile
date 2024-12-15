package com.quickness.data.request

import kotlinx.serialization.Serializable

@Serializable
data class AuthUserRequest(val token: String)
