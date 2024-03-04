package com.mizani.labis.data.dto.store

import com.mizani.labis.data.entity.ProductCategoryEntity

data class ProductCategoryDto(
    val id: Long = 0,
    val name: String = "",
    val storeId: Long = 0
)

fun ProductCategoryDto.toProductCategoryEntity(): ProductCategoryEntity {
    return ProductCategoryEntity(
        id = id,
        name = name,
        storeId = storeId
    )
}