package com.mizani.labis.data.request.product

import com.google.gson.annotations.SerializedName

data class ProductUpdateRequest(
    val id: Int? = null,
    val name: String? = null,
    @SerializedName("type_id")
    val typeId: Int? = null,
    @SerializedName("type_name")
    val typeName: String? = null,
    val price: Int? = null,
    val profit: Int? = null,
    val quantity: Int? = null,
    val active: Int? = null,
    val delete: Int? = null,
    @SerializedName("product_code")
    val productCode: String? = null,
    @SerializedName("store_id")
    val storeId: Int? = null
)