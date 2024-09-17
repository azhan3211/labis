package com.mizani.labis.data.remote.order.report

import com.google.gson.annotations.SerializedName

data class OrderReportResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("user_input_id")
    val userInputId: Int,
    @SerializedName("employee_id_order")
    val employeeIdOrder: Any?, // Use Int? if you expect it to be an integer, else Any?
    @SerializedName("employee_id_finish")
    val employeeIdFinish: Any?,
    @SerializedName("employee_id_taken")
    val employeeIdTaken: Any?,
    @SerializedName("employee_id_cancel")
    val employeeIdCancel: Any?,
    @SerializedName("take_away")
    val takeAway: Int,
    @SerializedName("queue_number")
    val queueNumber: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: Any?,
    @SerializedName("status")
    val status: Int,
    @SerializedName("is_deleted")
    val isDeleted: Int,
    @SerializedName("finished_at")
    val finishedAt: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("total_price")
    val totalPrice: Int,
    @SerializedName("total_profit")
    val totalProfit: Int,
    @SerializedName("debts")
    val debts: Any?,
    @SerializedName("order_products")
    val orderProducts: List<OrderReportItemResponse>
)