package com.quickness.data.request

import kotlinx.serialization.Serializable

/**
 * Data class representing the user registration request.
 *
 * This request is used to register a new user by providing necessary details.
 *
 * @property name The full name of the user.
 * @property email The email address of the user.
 * @property password The password for the user's account.
 * @property curp The CURP (Clave Única de Registro de Población) identifier for the user.
 * @property phone The phone number of the user.
 */
@Serializable
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val curp: String,
    val phone: String
)
