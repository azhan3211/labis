package com.mizani.labis.ui.screen.store.navigation

sealed class StoreRoute(val route: String) {

    object StoreDetailScreen : StoreRoute("store_detail_screen")
    object StoreCreateScreen : StoreRoute("store_create_screen")
    object StoreListScreen : StoreRoute("store_list_screen")

}