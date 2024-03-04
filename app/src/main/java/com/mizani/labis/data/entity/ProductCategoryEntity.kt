package com.mizani.labis.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mizani.labis.data.dto.store.ProductCategoryDto

@Entity(tableName = "labis_product_category")
data class ProductCategoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Long,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("store_id")
    val storeId: Long
)

fun ProductCategoryEntity.toProductCategoryDto(): ProductCategoryDto {
    return ProductCategoryDto(
        id = id,
        name = name,
        storeId = storeId
    )
}