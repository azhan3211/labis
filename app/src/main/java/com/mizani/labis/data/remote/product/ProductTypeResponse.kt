package com.mizani.labis.data.remote.product

import com.google.gson.annotations.SerializedName
import com.mizani.labis.domain.model.dto.ProductCategoryDto

data class ProductTypeResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("store_id")
    val storeId: Int
) {
    companion object {
        fun ProductTypeResponse.toDto(): ProductCategoryDto {
            return ProductCategoryDto(
                id = id.toLong(),
                name = name,
                storeId = storeId.toLong()
            )
        }
    }
}