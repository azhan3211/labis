package com.mizani.labis.ui.screen.auth.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mizani.labis.ui.screen.auth.AuthViewModel
import com.mizani.labis.ui.screen.auth.LoginScreen
import com.mizani.labis.ui.screen.auth.OtpScreen
import com.mizani.labis.ui.screen.auth.RegisterScreen

@Composable
fun AuthNavigation(
    navigateToHomeActivity: (() -> Unit) = { }
) {

    val viewModel: AuthViewModel = hiltViewModel()

    val navController = rememberNavController()

    LaunchedEffect(viewModel.isLoginSuccess.value) {
        if (viewModel.isLoginSuccess.value) {
            navigateToHomeActivity.invoke()
        }
    }

    NavHost(
        navController = navController,
        startDestination = AuthNavigationRoute.LoginScreen.route
    ) {
        composable(route = AuthNavigationRoute.LoginScreen.route) {
            LoginScreen(
                loginError = viewModel.loginError.value,
                onLoginClicked = { email, password ->
                    viewModel.login(email, password)
                },
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