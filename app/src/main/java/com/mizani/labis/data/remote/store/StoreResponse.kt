package com.mizani.labis.data.remote.store

import com.google.gson.annotations.SerializedName
import com.mizani.labis.domain.model.dto.StoreDto

data class StoreResponse(
    @SerializedName("data")
    val stores: List<StoreDataResponse>
)