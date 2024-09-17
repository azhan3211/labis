package com.mizani.labis.data.remote.order.statistic
import com.google.gson.annotations.SerializedName

data class OrderStatisticResponse(
    @SerializedName("capital")
    val capital: Int,
    @SerializedName("profit")
    val profit: Int,
    @SerializedName("available_capital")
    val availableCapital: Int,
    @SerializedName("expenditure")
    val expenditure: Int,
    @SerializedName("debt")
    val debt: Int,
    @SerializedName("salary")
    val salary: Int,
    @SerializedName("start")
    val startDate: String,
    @SerializedName("end")
    val endDate: String
)