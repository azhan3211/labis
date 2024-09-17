package com.mizani.labis.data.repository

import com.mizani.labis.data.local.OrderDao
import com.mizani.labis.data.mapper.OrderMapper.toDto
import com.mizani.labis.data.mapper.OrderMapper.toRequest
import com.mizani.labis.data.mapper.OrderMapper.toRequestMap
import com.mizani.labis.data.remote.order.OrderService
import com.mizani.labis.data.request.order.report.OrderReportRequest
import com.mizani.labis.domain.model.dto.ErrorDto
import com.mizani.labis.domain.model.dto.OrderDto
import com.mizani.labis.domain.model.dto.OrderStatisticDto
import com.mizani.labis.domain.model.dto.Result
import com.mizani.labis.domain.model.dto.toEntity
import com.mizani.labis.domain.model.entity.toDto
import com.mizani.labis.domain.repository.OrderRepository
import com.mizani.labis.utils.LabisDateUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderService: OrderService,
    private val orderDao: OrderDao
) : OrderRepository {
    override suspend fun saveOrder(orderDto: OrderDto): Result<String, ErrorDto> {
        return try {
            val response = orderService.saveOrder(orderDto.toRequest())
            if (response.isSuccessful) {
                Result.Success("Create order success")
            } else {
                Result.Error(ErrorDto(response.errorBody().toString()))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.Error(ErrorDto(e.message.toString()))
        }
    }

    override suspend fun deleteOrder(id: Long) {
        orderDao.delete(id)
    }

    override suspend fun getOrders(storeId: Int, date: Date): Result<List<OrderDto>, ErrorDto> {
        return try {
            val orderRequest = OrderReportRequest(
                storeId = storeId,
                date = date
            )
            val response = orderService.getOrders(orderRequest.toRequestMap())
            if (response.isSuccessful) {
                Result.Success(response.body()?.data?.map { it.toDto() } ?: listOf())
            } else {
                Result.Error(ErrorDto(response.errorBody().toString()))
            }
        } catch (e: Exception) {
            return Result.Error(ErrorDto(e.message.toString()))
        }
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
        startDate: Date?,
        endDate: Date?
    ): Flow<List<OrderDto>> {
        return flow {
            if (startDate == null || endDate == null) {
                orderDao.getAllOrdersPayLater(
                    storeId
                ).collect {
                    emit(
                        it.map { orderEntity ->
                            orderEntity.toDto()
                        }
                    )
                }
            } else {
                orderDao.getOrdersPayLater(
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

    override suspend fun paidDebt(orderDto: OrderDto) {
        orderDao.insert(orderDto.toEntity())
    }

    override suspend fun getOrderStatistic(
        storeId: Int,
        date: Date
    ): Result<OrderStatisticDto, ErrorDto> {
        return try {
            val orderRequest = OrderReportRequest(
                storeId = storeId,
                date = date
            )
            val response = orderService.getOrderStatistic(orderRequest.toRequestMap())
            if (response.isSuccessful) {
                Result.Success(response.body()?.data?.toDto() ?: OrderStatisticDto())
            } else {
                Result.Error(ErrorDto(response.errorBody().toString()))
            }
        } catch (e: Exception) {
            Result.Error(ErrorDto(e.message.toString()))
        }
    }

//    override suspend fun getOrders(
//        storeId: Long,
//        startDate: Date,
//        endDate: Date
//    ): Flow<List<OrderDto>> {
//        return flow {
//            orderDao.getAllOrders(
//                storeId,
//                LabisDateUtils.getDateStartOfDay(startDate),
//                LabisDateUtils.getDateEndOfDay(endDate)
//            ).collect {
//                emit(
//                    it.map { orderEntity ->
//                        orderEntity.toDto()
//                    }
//                )
//            }
//        }
//    }
}