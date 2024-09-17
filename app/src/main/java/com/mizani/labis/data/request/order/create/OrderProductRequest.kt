package com.mizani.labis.data.request.order.create

import com.google.gson.annotations.SerializedName

data class OrderProductRequest(
    val quantity: Int,
    @SerializedName("product_id")
    val productId: Int
)