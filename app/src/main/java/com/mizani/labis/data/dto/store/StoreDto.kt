package com.mizani.labis.data.dto.store

import com.mizani.labis.data.entity.StoreEntity

data class StoreDto(
    val id: Long = 0,
    val name: String = "",
    val address: String = ""
)

fun StoreDto.toStoreEntity(): StoreEntity {
    return StoreEntity(
        id = id,
        name = name,
        address = address
    )
}