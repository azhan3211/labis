package com.mizani.labis.ui.screen.store.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mizani.labis.R
import com.mizani.labis.ui.screen.store.StoreCreateScreen
import com.mizani.labis.ui.screen.store.StoreDetailScreen
import com.mizani.labis.ui.screen.store.StoreListScreen
import com.mizani.labis.ui.screen.store.StoreViewModel
import com.mizani.labis.utils.DialogUtils

@Composable
fun StoreNavigation(
    startDestination: String = StoreRoute.StoreListScreen.route,
    navigateToProductActivity: ((Long) -> Unit)? = null,
    backListener: ((Boolean) -> Unit)? = null
) {

    val navController = rememberNavController()
    val storeViewModel: StoreViewModel = hiltViewModel()
    val context = LocalContext.current

    storeViewModel.getStores()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = StoreRoute.StoreListScreen.route) {
            StoreListScreen(
                stores = storeViewModel.store,
                navigateToCreateStoreScreen = {
                    navController.navigate(StoreRoute.StoreCreateScreen.route)
                },
                onSelectStoreListener = {
                    storeViewModel.setSelectedStore(it.id)
                    backListener?.invoke(true)
                },
                navigateToDetailStoreScreen = {
                    storeViewModel.getSelected(it)
                    navController.navigate(StoreRoute.StoreDetailScreen.route)
                },
                onDeleteListener = {
                    DialogUtils.confirmationDialog(
                        context,
                        context.getString(R.string.delete_store),
                        context.getString(R.string.are_you_sure_want_to_delete_this_store),
                        dismissListener = null,
                        okListener = {
                            storeViewModel.deleteStore(it)
                        }
                    )
                },
                backListener = {
                    backListener?.invoke(false)
                }
            )
        }
        composable(route = StoreRoute.StoreDetailScreen.route) {
            StoreDetailScreen(
                storeDto = storeViewModel.selectedStore.value,
                backListener = {
                    navController.popBackStack()
                },
                onUpdateListener = {
                    storeViewModel.saveStore(it)
                    navController.popBackStack()
                    Toast.makeText(
                        context,
                        context.getString(R.string.update_store_success),
                        Toast.LENGTH_SHORT
                    ).show()
                },
                onProductClicked = {
                    navigateToProductActivity?.invoke(it)
                }
            )
        }
        composable(route = StoreRoute.StoreCreateScreen.route) {
            StoreCreateScreen(
                backListener = {
                    navController.popBackStack()
                },
                onSaveClicked = {
                    storeViewModel.saveStore(it)
                    navController.popBackStack()
                    Toast.makeText(
                        context,
                        context.getString(R.string.create_store_success),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }
}