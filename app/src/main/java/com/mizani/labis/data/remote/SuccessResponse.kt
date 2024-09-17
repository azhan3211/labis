package com.mizani.labis.data.remote

import com.google.gson.annotations.SerializedName

data class SuccessResponse<T>(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: T? = null
)