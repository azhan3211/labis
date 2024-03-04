package com.mizani.labis.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mizani.labis.data.dto.store.ProductDto

@Entity(tableName = "labis_product")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Long = 0,
    @ColumnInfo("store_id")
    val storeId: Long,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("capital")
    val capital: Int,
    @ColumnInfo("price_to_sell")
    val priceToSell: Int,
    @ColumnInfo("stock")
    val stock: Int,
    @ColumnInfo("description")
    val description: String,
    @ColumnInfo("category_id")
    val categoryId: Long
//    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB)
//    val image: List<Byte>
)

fun ProductEntity.toProductDto(): ProductDto {
    return ProductDto(
        id = id,
        storeId = storeId,
        name = name,
        capital = capital,
        priceToSell = priceToSell,
        stock = stock,
        description = description,
        categoryId = categoryId
//        image = image
    )
}