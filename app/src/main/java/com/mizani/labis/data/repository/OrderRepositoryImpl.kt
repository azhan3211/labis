package com.mizani.labis.data.repository

import android.util.Log
import com.mizani.labis.data.local.OrderDao
import com.mizani.labis.domain.model.dto.OrderDto
import com.mizani.labis.domain.model.dto.toEntity
import com.mizani.labis.domain.model.entity.toDto
import com.mizani.labis.domain.repository.OrderRepository
import com.mizani.labis.utils.LabisDateUtils
import com.mizani.labis.utils.LabisDateUtils.Companion.toReadableView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderDao: OrderDao
) : OrderRepository {
    override suspend fun saveOrder(orderDto: OrderDto) {
        orderDao.insert(orderDto.toEntity())
    }

    override suspend fun deleteOrder(id: Long) {
        orderDao.delete(id)
    }

    override suspend fun getOrderPaid(storeId: Long, startDate: Date, endDate: Date): Flow<List<OrderDto>> {
        return flow {
            orderDao.getAllOrdersPaid(
                storeId,
                LabisDateUtils.getDateStartOfDay(startDate),
                LabisDateUtils.getDateEndOfDay(endDate)
            ).collect {
                emit(
                    it.map { orderEntity ->
                        orderEntity.toDto()
                    }
                )
            }
        }
    }

    override suspend fun getOrderUnpaid(
        storeId: Long,
        startDate: Date,
        endDate: Date
    ): Flow<List<OrderDto>> {
        return flow {
            orderDao.getAllOrdersPayLater(
                storeId,
                LabisDateUtils.getDateStartOfDay(startDate),
                LabisDateUtils.getDateEndOfDay(endDate)
            ).collect {
                emit(
                    it.map { orderEntity ->
                        orderEntity.toDto()
                    }
                )
            }
        }
    }

    override suspend fun paidDebt(orderDto: OrderDto) {
        orderDao.insert(orderDto.toEntity())
    }

    override suspend fun getOrders(
        storeId: Long,
        startDate: Date,
        endDate: Date
    ): Flow<List<OrderDto>> {
        return flow {
            orderDao.getAllOrders(
                storeId,
                LabisDateUtils.getDateStartOfDay(startDate),
                LabisDateUtils.getDateEndOfDay(endDate)
            ).collect {
                emit(
                    it.map { orderEntity ->
                        orderEntity.toDto()
                    }
                )
            }
        }
    }
}