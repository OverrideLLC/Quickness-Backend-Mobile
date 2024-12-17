package com.quickness.utils

sealed class Routes(val route: String) {
    data object AuthRoute: Routes("/auth")
    data object RegisterRoute: Routes("/register")
}