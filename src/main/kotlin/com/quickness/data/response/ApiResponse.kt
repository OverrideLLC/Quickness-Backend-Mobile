package com.quickness.data.response

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 * Represents a standardized response format for API interactions.
 *
 * This class encapsulates a message, a status code, and optional data in JSON format
 * to provide consistent responses across the application.
 *
 * @property message A descriptive message about the result of the API operation.
 * @property status The HTTP-like status code representing the operation's result.
 * @property data Additional information related to the response, formatted as a JSON object.
 */
@Serializable
data class ApiResponse(
    val message: String,
    val status: Int,
    val data: JsonObject
)