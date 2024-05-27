package com.mizani.labis.ui.screen.report.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun ReportNavigation(

) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ReportRoute.ReportScreen.route) {

    }
}