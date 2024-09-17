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
    val categoryId: Long = 0,
    val categoryName: String = ""
//    val image: List<Byte>
) {
    fun profit(): Int = priceToSell - capital
}

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

fun ProductDto.toUpdateRequest(currentProductDto: ProductDto): HashMap<String, String> {
    val hashMap = hashMapOf<String, String>()
    hashMap["id"] = id.toString()
    hashMap["store_id"] = storeId.toString()
    if (name != currentProductDto.name) {
        hashMap["name"] = name
    }
    if (categoryId.toInt() != currentProductDto.categoryId.toInt()) {
        hashMap["type_id"] = categoryId.toString()
    }
    if (categoryName != currentProductDto.categoryName) {
        hashMap["type_name"] = categoryName
    }
    if (priceToSell != currentProductDto.priceToSell) {
        hashMap["price"] = priceToSell.toString()
    }
    if (profit() != currentProductDto.profit()) {
        hashMap["profit"] = profit().toString()
    }
    if (stock != currentProductDto.stock) {
        hashMap["quantity"] = stock.toString()
    }
    return hashMap
}