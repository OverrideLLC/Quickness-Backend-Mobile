package com.quickness.utils

sealed class Routes(val route: String) {
    data object ApiRoute: Routes("quickness/mobile/api/v0")
    data object AuthRoute: Routes("/auth")
}