package com.mizani.labis.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mizani.labis.domain.model.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Upsert
    suspend fun insert(productEntity: ProductEntity)

    @Query("SELECT * FROM labis_product WHERE store_id = :storeId")
    fun getAll(storeId: Long): Flow<List<ProductEntity>>

    @Query("SELECT * FROM labis_product WHERE id = :id")
    suspend fun get(id: Long): ProductEntity

    @Query("DELETE FROM labis_product WHERE id = :id")
    suspend fun delete(id: Long)
}