package com.mizani.labis.domain.model.dto

import com.mizani.labis.domain.model.entity.OrderEntity
import com.mizani.labis.domain.model.entity.OrdersEntity
import java.io.Serializable
import java.util.Date

data class OrderDto(
    val id: Long = 0,
    val name: String = "",
    val dateTime: Date = Date(),
    val storeId: Long = 0,
    val status: String = "",
    val orders: List<OrdersDto> = listOf()
): Serializable {

    fun isPayLater(): Boolean {
        return status != PAID && status != CASH
    }

    companion object {
        const val PAID = "PAID"
        const val UNPAID = "UNPAID"
        const val CASH = "CASH"
    }
}

data class OrdersDto(
    val id: Long,
    val name: String,
    val capital: Int,
    val price: Int,
    var count: Int = 0
): Serializable

fun OrdersDto.toProductDto(): ProductDto {
    return ProductDto(
        id = id,
        name = name,
        capital = capital,
        priceToSell = price,
    )
}

fun OrderDto.toEntity(): OrderEntity {
    return OrderEntity(
        id = id,
        storeId = storeId,
        name = name,
        orders = orders.map {
            it.toEntity()
        },
        dateTime = dateTime,
        status = status
    )
}

fun OrdersDto.toEntity(): OrdersEntity {
    return OrdersEntity(
        id = id,
        productName = name,
        price = price,
        capital = capital,
        count = count
    )
}