package com.mizani.labis.ui.screen.home.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mizani.labis.domain.model.dto.OrdersDto
import com.mizani.labis.domain.model.dto.StoreDto
import com.mizani.labis.ui.screen.home.MenuScreen
import com.mizani.labis.ui.screen.home.HomeViewModel
import com.mizani.labis.ui.screen.home.ProfileScreen
import com.mizani.labis.ui.screen.home.ReportScreen
import java.util.Date

@Composable
fun HomeNavigation(
    storeChanged: Boolean = false,
    navController: NavHostController = rememberNavController(),
    resetStoreChanged: () -> Unit = {},
    navigateToStoreActivity: (() -> Unit)? = null,
    navigateToOrderActivity: ((List<OrdersDto>) -> Unit)? = null,
    navigateToReportActivity: (Date, Date, String) -> Unit = { _, _, _ ->}
) {

    val homeViewModel: HomeViewModel = hiltViewModel()
    homeViewModel.getProducts()
    homeViewModel.getProductCategories()
    homeViewModel.getSelectedStore()
    homeViewModel.getOrder()
    homeViewModel.getOrderUnpaid()

    LaunchedEffect(storeChanged) {
        if (storeChanged) {
            homeViewModel.getProducts()
            homeViewModel.getProductCategories()
            homeViewModel.getSelectedStore()
            resetStoreChanged.invoke()
        }
    }

    NavHost(navController = navController, startDestination = HomeRoute.MenuScreen.route) {
        composable(route = HomeRoute.MenuScreen.route) {
            MenuScreen(
                selectedStore = homeViewModel.selectedStore.value ?: StoreDto(),
                categories = homeViewModel.categories,
                products = homeViewModel.products,
                totalPrice = homeViewModel.totalPrice.value,
                onStoreClicked = navigateToStoreActivity,
                onOrderClicked = {
                    navigateToOrderActivity?.invoke(homeViewModel.orders)
                },
                onInc = {
                    homeViewModel.onOrderInc(it)
                },
                onDec = {
                    homeViewModel.onOrderDec(it)
                }
            )
        }
        composable(route = HomeRoute.ReportScreen.route) {
            ReportScreen(
                totalSalePaid = homeViewModel.paidSale.value,
                totalSaleUnpaid = homeViewModel.unpaidSale.value,
                onReportClicked = { category ->
                    navigateToReportActivity.invoke(
                        homeViewModel.selectedStartDate.value,
                        homeViewModel.selectedEndDate.value,
                        category
                    )
                },
                onDateChange = { startDate, endDate ->
                    homeViewModel.setSelectedDate(startDate, endDate)
                    homeViewModel.getOrder(startDate, endDate)
                    homeViewModel.getOrderUnpaid(startDate, endDate)
                }
            )
        }
        composable(route = HomeRoute.ProfileScreen.route) {
            ProfileScreen()
        }
    }
}