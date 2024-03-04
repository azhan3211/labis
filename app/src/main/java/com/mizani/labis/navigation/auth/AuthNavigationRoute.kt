package com.mizani.labis.navigation.auth

sealed class AuthNavigationRoute(val route: String) {

    object SplashScreen: AuthNavigationRoute("splash_screen")
    object LoginScreen: AuthNavigationRoute("login_screen")
    object RegisterScreen: AuthNavigationRoute("register_screen")
    object OtpScreen: AuthNavigationRoute("otp_screen")

}