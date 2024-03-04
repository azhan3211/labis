package com.mizani.labis.navigation.home

sealed class HomeRoute(val route: String) {

    object MenuScreen : HomeRoute("menu_screen")
    object ReportScreen : HomeRoute("report_screen")
    object ProfileScreen : HomeRoute("profile_screen")

}
