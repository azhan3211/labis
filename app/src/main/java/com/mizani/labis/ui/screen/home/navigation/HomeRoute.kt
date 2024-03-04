package com.mizani.labis.ui.screen.home.navigation

sealed class HomeRoute(val route: String) {

    object MenuScreen : HomeRoute("menu_screen")
    object ReportScreen : HomeRoute("report_screen")
    object ProfileScreen : HomeRoute("profile_screen")

}
