package com.mizani.labis.data.remote.order.report

import com.google.gson.annotations.SerializedName

data class OrderReportProductResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("photo")
    val photo: Any?,
    @SerializedName("active")
    val active: Int
)