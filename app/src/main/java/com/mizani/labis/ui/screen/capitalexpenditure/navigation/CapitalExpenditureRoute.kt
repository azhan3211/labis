package com.mizani.labis.ui.screen.capitalexpenditure.navigation

sealed class CapitalExpenditureRoute(val route: String) {

    object CapitalExpenditureListScreen : CapitalExpenditureRoute("capital_expenditure_list_screen")
    object CapitalExpenditureCreateScreen : CapitalExpenditureRoute("capital_expenditure_create_screen")

}