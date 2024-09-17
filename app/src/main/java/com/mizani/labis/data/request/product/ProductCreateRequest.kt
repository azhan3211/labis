package com.mizani.labis.data.request.product

import com.google.gson.annotations.SerializedName

data class ProductCreateRequest(
    @SerializedName("store_id")
    val storeId: Int,
    val name: String,
    val price: Int,
    val profit: Int,
    @SerializedName("type_id")
    val typeId: Int,
    val quantity: Int,
    val active: Int,
    @SerializedName("type_name")
    val typeName: String? = null
)