package com.mizani.labis.data.mapper

import com.mizani.labis.data.remote.order.report.OrderReportItemResponse
import com.mizani.labis.data.remote.order.report.OrderReportResponse
import com.mizani.labis.data.remote.order.statistic.OrderStatisticResponse
import com.mizani.labis.data.request.order.create.OrderProductRequest
import com.mizani.labis.data.request.order.create.OrderRequest
import com.mizani.labis.data.request.order.report.OrderReportRequest
import com.mizani.labis.domain.model.dto.OrderDto
import com.mizani.labis.domain.model.dto.OrderStatisticDto
import com.mizani.labis.domain.model.dto.OrdersDto
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object OrderMapper {

    fun OrderDto.toRequest(): OrderRequest {
        return OrderRequest(
            storeId = storeId.toInt(),
            totalPrice = totalPrice,
            totalProfit = totalProfit,
            takeAway = 0,
            tax = 0,
            product = orders.map { it.toRequest() }
        )
    }

    private fun OrdersDto.toRequest(): OrderProductRequest {
        return OrderProductRequest(
            quantity = count,
            productId = id.toInt()
        )
    }

    fun OrderReportRequest.toRequestMap(): HashMap<String, String> {
        val cal = Calendar.getInstance()
        cal.time = date
        val data = hashMapOf<String, String>()
        data["store_id"] = storeId.toString()
        data["year"] = cal.get(Calendar.YEAR).toString()
        data["month"] = cal.get(Calendar.MONTH).inc().toString()
        data["date"] = cal.get(Calendar.DAY_OF_MONTH).toString()
        return data
    }

    fun OrderReportResponse.toDto(): OrderDto {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK)
        return OrderDto(
            id = id.toLong(),
            name = name,
            storeId = 0,
            totalProfit = totalProfit,
            totalPrice = totalPrice,
            dateTime = format.parse(date) ?: Date(),
            orders = orderProducts.map { it.toDto() },
            status = OrderDto.CASH
        )
    }

    private fun OrderReportItemResponse.toDto(): OrdersDto {
        return OrdersDto(
            name = product.name,
            capital = price - profit,
            price = price,
            count = quantity,
            id = productId.toLong()
        )
    }

    fun OrderStatisticResponse.toDto(): OrderStatisticDto {
        return OrderStatisticDto(
            capital = capital,
            profit = profit,
            expenditure = expenditure,
            endDate = startDate,
            startDate = startDate,
            availableCapital = availableCapital,
            debt = debt,
            salary = salary
        )
    }

}