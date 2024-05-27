package com.mizani.labis.domain.repository

import com.mizani.labis.domain.model.dto.OrderDto
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface OrderRepository {

    suspend fun saveOrder(orderDto: OrderDto)

    suspend fun deleteOrder(id: Long)

    suspend fun getOrders(
        storeId: Long,
        startDate: Date = Date(),
        endDate: Date = Date()
    ): Flow<List<OrderDto>>

    suspend fun getOrderPaid(
        storeId: Long,
        startDate: Date = Date(),
        endDate: Date = Date()
    ): Flow<List<OrderDto>>

    suspend fun getOrderUnpaid(
        storeId: Long,
        startDate: Date = Date(),
        endDate: Date = Date()
    ): Flow<List<OrderDto>>

    suspend fun paidDebt(orderDto: OrderDto)

}