package com.mizani.labis.data.request.product

import com.google.gson.annotations.SerializedName

data class ProductTypeCreateRequest(
    val name: String,
    @SerializedName("store_id")
    val storeId: Int
)