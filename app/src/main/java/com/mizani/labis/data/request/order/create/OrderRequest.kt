package com.mizani.labis.data.request.order.create

import com.google.gson.annotations.SerializedName

data class OrderRequest(
    @SerializedName("store_id")
    val storeId: Int,
    @SerializedName("name")
    val name: String = "Guest",
    @SerializedName("tax")
    val tax: Int = 0,
    @SerializedName("take_away")
    val takeAway: Int = 0,
    @SerializedName("product")
    val product: List<OrderProductRequest>,
    @SerializedName("total_price")
    val totalPrice: Int,
    @SerializedName("total_profit")
    val totalProfit: Int
)