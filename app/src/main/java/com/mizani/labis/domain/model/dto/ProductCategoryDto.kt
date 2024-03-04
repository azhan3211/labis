package com.mizani.labis.domain.model.dto

import com.mizani.labis.domain.model.entity.ProductCategoryEntity

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