package com.mizani.labis.domain.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mizani.labis.domain.model.dto.OrderDto
import com.mizani.labis.domain.model.dto.OrdersDto
import java.util.Date

@Entity(tableName = "labis_order")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Long,
    @ColumnInfo("store_id")
    val storeId: Long,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("orders")
    val orders: List<OrdersEntity>,
    @ColumnInfo("status")
    val status: String,
    @ColumnInfo("date_time")
    val dateTime: Date
)

data class OrdersEntity(
    @ColumnInfo("id")
    val id: Long,
    @ColumnInfo("product_name")
    val productName: String,
    @ColumnInfo("price")
    val price: Int,
    @ColumnInfo("capital")
    val capital: Int,
    @ColumnInfo("count")
    val count: Int
)

fun OrderEntity.toDto(): OrderDto {
    return OrderDto(
        id = id,
        name = name,
        dateTime = dateTime,
        status = status,
        orders = orders.map {
            it.toDto()
        },
        storeId = storeId
    )
}

fun OrdersEntity.toDto(): OrdersDto {
    return OrdersDto(
        id = id,
        name = productName,
        price = price,
        capital = capital,
        count = count,
    )
}