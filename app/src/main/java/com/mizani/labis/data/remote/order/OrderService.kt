package com.mizani.labis.data.remote.order

import com.mizani.labis.data.remote.SuccessResponse
import com.mizani.labis.data.remote.order.report.OrderReportResponse
import com.mizani.labis.data.remote.order.statistic.OrderStatisticResponse
import com.mizani.labis.data.request.order.create.OrderRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface OrderService {

    @POST("/api/auth/order/create")
    suspend fun saveOrder(
        @Body request: OrderRequest
    ): Response<SuccessResponse<Any>>

    @GET("/api/auth/report/order/product/user")
    suspend fun getOrders(
        @QueryMap query: HashMap<String, String>
    ): Response<SuccessResponse<List<OrderReportResponse>>>

    @GET("/api/auth/report/order/user")
    suspend fun getOrderStatistic(
        @QueryMap query: HashMap<String, String>
    ): Response<SuccessResponse<OrderStatisticResponse>>

}