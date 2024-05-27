package com.mizani.labis.ui.screen.product.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mizani.labis.R
import com.mizani.labis.ui.screen.ProductListScreen
import com.mizani.labis.ui.screen.product.ProductCreateScreen
import com.mizani.labis.ui.screen.product.ProductEditScreen
import com.mizani.labis.ui.screen.product.ProductViewModel
import com.mizani.labis.utils.DialogUtils

@Composable
fun ProductNavigation(
    storeId: Long,
    startDestination: String = ProductRoute.ProductListScreen.route,
    backListener: (() -> Unit)? = null
) {

    val context = LocalContext.current
    val navController = rememberNavController()
    val productViewModel: ProductViewModel = hiltViewModel()

    productViewModel.getProducts(storeId)
    productViewModel.getProductCategory(storeId)

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = ProductRoute.ProductListScreen.route) {
            ProductListScreen(
                products = productViewModel.products,
                onAddProductListener = {
                    navController.navigate(ProductRoute.ProductCreateScreen.route)
                },
                onDeleteListener = {
                    DialogUtils.confirmationDialog(
                        context = context,
                        title = context.getString(R.string.delete_product),
                        message = context.getString(R.string.are_you_sure_want_to_delete_this_product),
                        okListener = {
                            productViewModel.deleteProduct(it)
                        }
                    )
                },
                onProductClicked = {
                    productViewModel.setSelectProduct(it)
                    navController.navigate(ProductRoute.ProductEditScreen.route)
                },
                backListener = backListener
            )
        }
        composable(route = ProductRoute.ProductCreateScreen.route) {
            ProductCreateScreen(
                storeId = storeId,
                categories = productViewModel.productCategories,
                onSaveProduct = { product, productCategory ->
                    if (productCategory.id == 0L) {
                        productViewModel.saveCategoryAndProduct(
                            productCategory,
                            product
                        )
                    } else {
                        productViewModel.saveProduct(product)
                    }
                    navController.popBackStack()
                },
                backListener = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = ProductRoute.ProductEditScreen.route) {
            ProductEditScreen(
                productDto = productViewModel.selectedProduct.value,
                categories = productViewModel.productCategories,
                onSaveProduct = { product, productCategory ->
                    if (productCategory.id == 0L) {
                        productViewModel.saveCategoryAndProduct(
                            productCategory,
                            product
                        )
                    } else {
                        productViewModel.saveProduct(product)
                    }
                    navController.popBackStack()
                },
                onBackListener = {
                    backListener?.invoke()
                }
            )
        }
    }
}