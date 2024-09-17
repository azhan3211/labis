package com.mizani.labis.data.remote.expenditure

import com.google.gson.annotations.SerializedName

data class CapitalExpenditureResponse(
    @SerializedName("data")
    val data: List<CapitalExpenditureDataResponse> = listOf(),

    @SerializedName("available_capital")
    val availableCapital: Int = 0
)