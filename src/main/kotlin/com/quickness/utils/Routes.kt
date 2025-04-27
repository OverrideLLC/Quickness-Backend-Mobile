package com.quickness.utils

/**
 * A sealed class representing all the routes in the application.
 * This is used to define the different API routes that the application handles.
 *
 * @property route The string value of the route's URL path.
 */
sealed class Routes(val route: String) {

    /**
     * Represents the authentication route.
     * Used for API calls related to user authentication.
     */
    object AuthRoute : Routes("/auth")

    /**
     * Represents the registration route.
     * Used for API calls related to user registration.
     */
    object RegisterRoute : Routes("/register")
}
