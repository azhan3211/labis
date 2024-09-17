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
    reloadCapitalExpenditure: Boolean = false,
    navController: NavHostController = rememberNavController(),
    resetStoreChanged: () -> Unit = {},
    resetReloadCapitalExpenditure: () -> Unit = {},
    navigateToStoreActivity: (() -> Unit) = {},
    navigateToOrderActivity: ((List<OrdersDto>) -> Unit)? = null,
    navigateToReportActivity: (Date, Date, String) -> Unit = { _, _, _ ->},
    navigateToCapitalExpenditure: (Date) -> Unit
) {

    val homeViewModel: HomeViewModel = hiltViewModel()

    LaunchedEffect(true) {
        homeViewModel.getProducts()
        homeViewModel.getProductCategories()
        homeViewModel.getSelectedStore()
        homeViewModel.getOrder()
        homeViewModel.getOrderUnpaid(null, null)
        homeViewModel.getStatistic()
    }

    LaunchedEffect(storeChanged) {
        if (storeChanged) {
            homeViewModel.getProducts()
            homeViewModel.getProductCategories()
            homeViewModel.getSelectedStore()
            homeViewModel.getStatistic()
            resetStoreChanged.invoke()
        }
    }

    LaunchedEffect(reloadCapitalExpenditure) {
        if (reloadCapitalExpenditure) {
            homeViewModel.getStatistic()
            resetReloadCapitalExpenditure.invoke()
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
                searchValue = homeViewModel.searchValue.value,
                onOrderClicked = {
                    navigateToOrderActivity?.invoke(homeViewModel.orders)
                },
                onInc = {
                    homeViewModel.onOrderInc(it)
                },
                onDec = {
                    homeViewModel.onOrderDec(it)
                },
                onSearch = {
                    homeViewModel.onSearch(it)
                }
            )
        }
        composable(route = HomeRoute.ReportScreen.route) {
            ReportScreen(
                totalSalePaid = homeViewModel.orderStatistic.value.getOmzet(),
                totalSaleUnpaid = homeViewModel.unpaidSale.value,
                capital = homeViewModel.orderStatistic.value.availableCapital,
                profit = homeViewModel.orderStatistic.value.profit,
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
                    homeViewModel.getStatistic()
                },
                onAvailableCapitalClicked = {
                    navigateToCapitalExpenditure.invoke(homeViewModel.selectedStartDate.value)
                }
            )
        }
        composable(route = HomeRoute.ProfileScreen.route) {
            ProfileScreen()
        }
    }
}