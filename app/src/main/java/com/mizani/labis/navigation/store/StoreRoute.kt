package com.mizani.labis.navigation.store

sealed class StoreRoute(val route: String) {

    object StoreDetailScreen : StoreRoute("store_detail_screen")
    object StoreCreateScreen : StoreRoute("store_create_screen")
    object StoreListScreen : StoreRoute("store_list_screen")

}