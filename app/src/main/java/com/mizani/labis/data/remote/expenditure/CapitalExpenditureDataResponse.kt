package com.mizani.labis.data.remote.expenditure

import com.google.gson.annotations.SerializedName

data class CapitalExpenditureDataResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("store_id")
    val storeId: Int,

    @SerializedName("price")
    val price: Int,

    @SerializedName("description")
    val description: String,

    @SerializedName("is_deleted")
    val isDeleted: Int,

    @SerializedName("date")
    val date: String
)