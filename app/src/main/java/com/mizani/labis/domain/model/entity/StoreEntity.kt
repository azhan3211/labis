package com.mizani.labis.domain.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mizani.labis.domain.model.dto.StoreDto

@Entity(tableName = "labis_store")
data class StoreEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Long = 0,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("address")
    val address: String
)

fun StoreEntity.toStoreDto(): StoreDto {
    return StoreDto(
        id = id,
        name = name,
        address = address
    )
}