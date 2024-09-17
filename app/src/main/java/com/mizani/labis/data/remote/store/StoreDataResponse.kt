package com.mizani.labis.data.remote.store

import com.google.gson.annotations.SerializedName
import com.mizani.labis.domain.model.dto.StoreDto

data class StoreDataResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("address")
    val address: String
) {
    companion object {

        fun StoreDataResponse.toDto(): StoreDto {
            return StoreDto(
                id = id.toLong(),
                name = name,
                address = address
            )
        }

    }
}