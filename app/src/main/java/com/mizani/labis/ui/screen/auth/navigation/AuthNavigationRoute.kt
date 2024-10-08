package com.mizani.labis.ui.screen.auth.navigation

sealed class AuthNavigationRoute(val route: String) {

    object LoginScreen: AuthNavigationRoute("login_screen")
    object RegisterScreen: AuthNavigationRoute("register_screen")
    object OtpScreen: AuthNavigationRoute("otp_screen")

}