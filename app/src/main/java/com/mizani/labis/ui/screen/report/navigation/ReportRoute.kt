package com.mizani.labis.ui.screen.report.navigation

sealed class ReportRoute(val route: String) {

    object ReportScreen : ReportRoute("report_screen")

}