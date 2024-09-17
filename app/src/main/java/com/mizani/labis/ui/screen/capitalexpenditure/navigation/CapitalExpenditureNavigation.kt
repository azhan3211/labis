package com.mizani.labis.ui.screen.capitalexpenditure.navigation

import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mizani.labis.R
import com.mizani.labis.ui.screen.capitalexpenditure.CapitalExpenditureCreateScreen
import com.mizani.labis.ui.screen.capitalexpenditure.CapitalExpenditureListScreen
import com.mizani.labis.ui.screen.capitalexpenditure.CapitalExpenditureViewModel
import com.mizani.labis.utils.DialogUtils
import java.util.Date

@Composable
fun CapitalExpenditureNavigation(
    date: Date,
    onBackListener: (Boolean) -> Unit
) {

    val navController = rememberNavController()
    val viewModel: CapitalExpenditureViewModel = hiltViewModel()
    val context = LocalContext.current

    LaunchedEffect(true) {
        viewModel.getCapitalExpenditure(date = date)
    }

    LaunchedEffect(viewModel.isSuccessCreateCapitalExpenditure.value) {
        if (viewModel.isSuccessCreateCapitalExpenditure.value) {
            navController.popBackStack()
            viewModel.getCapitalExpenditure(date = date)
            viewModel.resetSuccessCreateCapitalExpenditure()
        }
    }

    LaunchedEffect(viewModel.isSuccessDeleteCapitalExpenditure.value) {
        if (viewModel.isSuccessDeleteCapitalExpenditure.value) {
            viewModel.getCapitalExpenditure(date = date)
            viewModel.resetSuccessDeleteCapitalExpenditure()
        }
    }

    LaunchedEffect(viewModel.errorCapitalExpenditure.value) {
        if (viewModel.errorCapitalExpenditure.value.isNotEmpty()) {
            Toast.makeText(context, viewModel.errorCapitalExpenditure.value, LENGTH_SHORT).show()
            viewModel.resetError()
        }
    }

    NavHost(navController = navController, startDestination = CapitalExpenditureRoute.CapitalExpenditureListScreen.route) {
        composable(route = CapitalExpenditureRoute.CapitalExpenditureListScreen.route) {
            CapitalExpenditureListScreen(
                date = date,
                onBackListener = {
                    onBackListener.invoke(viewModel.isDataChanged.value)
                },
                capitalExpenditureDataDtos = viewModel.capitalExpendituresDto,
                totalExpenditure = viewModel.totalCapitalExpenditurePrice.value,
                onAddListener = {
                    navController.navigate(CapitalExpenditureRoute.CapitalExpenditureCreateScreen.route)
                },
                onDeleteListener = {
                    DialogUtils.confirmationDialog(
                        context = context,
                        title = context.getString(R.string.delete_capital_expenditure),
                        message = context.getString(R.string.are_you_sure_want_to_delete_this_capital_expenditure),
                        okListener = {
                            viewModel.deleteCapitalExpenditure(it.id)
                        }
                    )
                },
                onEditListener = {

                }
            )
        }
        composable(route = CapitalExpenditureRoute.CapitalExpenditureCreateScreen.route) {
            CapitalExpenditureCreateScreen(
                onAddCapitalExpenditure = {
                    viewModel.createCapitalExpenditure(it)
                },
                onBackListener = {
                    navController.popBackStack()
                }
            )
        }
    }

}