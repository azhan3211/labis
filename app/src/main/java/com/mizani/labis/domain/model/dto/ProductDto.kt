package com.mizani.labis.domain.model.dto

import com.mizani.labis.domain.model.entity.ProductEntity

data class ProductDto(
    val id: Long = 0,
    val storeId: Long = 0,
    val name: String = "",
    val capital: Int = 0,
    val stock: Int = 0,
    val description: String = "",
    val priceToSell: Int = 0,
    val categoryId: Long = 0
//    val image: List<Byte>
)

fun ProductDto.toProductEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        storeId = storeId,
        name = name,
        capital = capital,
        priceToSell = priceToSell,
        stock = stock,
        description = description,
        categoryId = categoryId,
//        image = image
    )
}

fun ProductDto.toOrderDto(): OrdersDto {
    return OrdersDto(
        id = id,
        name = name,
        price = priceToSell,
        capital = capital,
        count = 0
    )
}