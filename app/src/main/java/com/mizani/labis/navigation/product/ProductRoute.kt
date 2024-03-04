package com.mizani.labis.navigation.product

sealed class ProductRoute(val route: String) {

    object ProductCreateScreen : ProductRoute("product_create_screen")
    object ProductListScreen : ProductRoute("product_list_screen")

}