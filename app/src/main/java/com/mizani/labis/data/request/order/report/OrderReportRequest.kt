package com.mizani.labis.data.request.order.report

import java.util.Date

data class OrderReportRequest(
    val storeId: Int,
    val date: Date
)