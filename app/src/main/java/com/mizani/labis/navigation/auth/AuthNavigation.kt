package com.mizani.labis.navigation.auth

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mizani.labis.ui.screen.auth.LoginScreen
import com.mizani.labis.ui.screen.auth.OtpScreen
import com.mizani.labis.ui.screen.auth.RegisterScreen
import com.mizani.labis.ui.screen.auth.SplashScreen

@Composable
fun AuthNavigation(
    navigateToHomeActivity: (() -> Unit)? = null
) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AuthNavigationRoute.SplashScreen.route) {
        composable(route = AuthNavigationRoute.SplashScreen.route) {
            SplashScreen {
                navController.navigate(AuthNavigationRoute.LoginScreen.route) {
                    popUpTo(AuthNavigationRoute.SplashScreen.route) {
                        inclusive = true
                    }
                }
            }
        }
        composable(route = AuthNavigationRoute.LoginScreen.route) {
            LoginScreen(
                onLoginClicked = navigateToHomeActivity,
                navigateToRegister = {
                    navController.navigate(AuthNavigationRoute.RegisterScreen.route)
                }
            )
        }
        composable(route = AuthNavigationRoute.RegisterScreen.route) {
            RegisterScreen(
                navController
            ) {
                navController.navigate(AuthNavigationRoute.LoginScreen.route) {
                    popUpTo(AuthNavigationRoute.RegisterScreen.route) {
                        inclusive = true
                    }
                }
            }
        }
        composable(route = AuthNavigationRoute.OtpScreen.route) {
            OtpScreen(navController)
        }
    }
}