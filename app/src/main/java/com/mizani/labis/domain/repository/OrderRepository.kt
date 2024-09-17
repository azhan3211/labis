package com.mizani.labis.domain.repository

import com.mizani.labis.domain.model.dto.ErrorDto
import com.mizani.labis.domain.model.dto.OrderDto
import com.mizani.labis.domain.model.dto.OrderStatisticDto
import com.mizani.labis.domain.model.dto.Result
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface OrderRepository {

    suspend fun saveOrder(orderDto: OrderDto): Result<String, ErrorDto>

    suspend fun deleteOrder(id: Long)

    suspend fun getOrders(
        storeId: Int,
        date: Date
    ): Result<List<OrderDto>, ErrorDto>

//    suspend fun getOrders(
//        storeId: Long,
//        startDate: Date = Date(),
//        endDate: Date = Date()
//    ): Flow<List<OrderDto>>

    suspend fun getOrderPaid(
        storeId: Long,
        startDate: Date = Date(),
        endDate: Date = Date()
    ): Flow<List<OrderDto>>

    suspend fun getOrderUnpaid(
        storeId: Long,
        startDate: Date?,
        endDate: Date?
    ): Flow<List<OrderDto>>

    suspend fun paidDebt(orderDto: OrderDto)

    suspend fun getOrderStatistic(storeId: Int, date: Date): Result<OrderStatisticDto, ErrorDto>

}