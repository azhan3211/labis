package com.mizani.labis.data.remote.product

import com.google.gson.annotations.SerializedName
import com.mizani.labis.domain.model.dto.ProductDto

class ProductResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("type_id")
    val typeId: Int,
    @SerializedName("price")
    val price: Int,
    @SerializedName("profit")
    val profit: Int,
    @SerializedName("product_code")
    val productCode: String?,
    @SerializedName("active")
    val active: Int,
    @SerializedName("photo")
    val photo: String?,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("store_id")
    val storeId: Int
) {
    companion object {
        fun ProductResponse.toDto(): ProductDto {
            return ProductDto(
                id = id.toLong(),
                name = name,
                storeId = storeId.toLong(),
                categoryName = "",
                priceToSell = price,
                capital = price - profit,
                description = "",
                stock = quantity,
                categoryId = typeId.toLong()
            )
        }
    }
}