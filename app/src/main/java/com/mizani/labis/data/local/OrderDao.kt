package com.mizani.labis.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mizani.labis.domain.model.dto.OrderDto
import com.mizani.labis.domain.model.entity.OrderEntity
import com.mizani.labis.utils.LabisDateUtils
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface OrderDao {

    @Upsert
    suspend fun insert(orderEntity: OrderEntity)

    @Query("SELECT * FROM labis_order WHERE (status = '${OrderDto.PAID}' OR status = '${OrderDto.CASH}') AND store_id = :storeId AND date_time BETWEEN :startDate AND :endDate")
    fun getAllOrdersPaid(
        storeId: Long,
        startDate: Date = LabisDateUtils.getDateStartOfDay(),
        endDate: Date = LabisDateUtils.getDateEndOfDay()
    ): Flow<List<OrderEntity>>

    @Query("SELECT * FROM labis_order WHERE status = '${OrderDto.UNPAID}' AND store_id = :storeId AND date_time BETWEEN :startDate AND :endDate")
    fun getOrdersPayLater(
        storeId: Long,
        startDate: Date = LabisDateUtils.getDateStartOfDay(),
        endDate: Date = LabisDateUtils.getDateEndOfDay()
    ): Flow<List<OrderEntity>>

    @Query("SELECT * FROM labis_order WHERE status = '${OrderDto.UNPAID}' AND store_id = :storeId")
    fun getAllOrdersPayLater(
        storeId: Long
    ): Flow<List<OrderEntity>>

    @Query("SELECT * FROM labis_order WHERE store_id = :storeId AND date_time BETWEEN :startDate AND :endDate")
    fun getAllOrders(
        storeId: Long,
        startDate: Date = LabisDateUtils.getDateStartOfDay(),
        endDate: Date = LabisDateUtils.getDateEndOfDay()
    ): Flow<List<OrderEntity>>

    @Query("DELETE FROM labis_order WHERE id = :id")
    fun delete(id: Long)

}