package com.mizani.labis.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mizani.labis.domain.model.entity.ProductCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductCategoryDao {

    @Upsert
    suspend fun insert(productCategoryEntity: ProductCategoryEntity): Long

    @Query("SELECT * FROM labis_product_category WHERE store_id = :storeId")
    fun getAll(storeId: Long): Flow<List<ProductCategoryEntity>>

    @Query("SELECT * FROM labis_product_category WHERE id = :id")
    suspend fun get(id: Long): ProductCategoryEntity

    @Query("DELETE FROM labis_product_category WHERE id = :id")
    suspend fun delete(id: Long)

}