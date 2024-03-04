package com.mizani.labis.navigation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mizani.labis.data.dto.store.StoreDto
import com.mizani.labis.ui.screen.home.MenuScreen
import com.mizani.labis.ui.screen.home.HomeViewModel
import com.mizani.labis.ui.screen.home.ProfileScreen
import com.mizani.labis.ui.screen.home.ReportScreen

@Composable
fun HomeNavigation(
    storeChanged: Boolean = false,
    navController: NavHostController = rememberNavController(),
    resetStoreChanged: () -> Unit = {},
    navigateToStoreActivity: (() -> Unit)? = null
) {

    val homeViewModel: HomeViewModel = hiltViewModel()
    homeViewModel.getProducts()
    homeViewModel.getProductCategories()
    homeViewModel.getSelectedStore()

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
                categories = homeViewModel.categries,
                products = homeViewModel.products,
                onStoreClicked = navigateToStoreActivity
            )
        }
        composable(route = HomeRoute.ReportScreen.route) {
            ReportScreen()
        }
        composable(route = HomeRoute.ProfileScreen.route) {
            ProfileScreen()
        }
    }
}