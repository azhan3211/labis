package com.mizani.labis.data.remote.order.report

import com.google.gson.annotations.SerializedName

data class OrderReportItemResponse(
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("variant_id")
    val variantId: Any?,
    @SerializedName("price")
    val price: Int,
    @SerializedName("price_total")
    val priceTotal: Int,
    @SerializedName("profit")
    val profit: Int,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("tax")
    val tax: Int,
    @SerializedName("date")
    val date: String,
    @SerializedName("product")
    val product: OrderReportProductResponse,
    @SerializedName("variant")
    val variant: Any?
)