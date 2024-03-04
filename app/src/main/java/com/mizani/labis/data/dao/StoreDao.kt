package com.mizani.labis.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mizani.labis.data.entity.StoreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StoreDao {

    @Upsert
    suspend fun insert(store: StoreEntity): Long

    @Query("SELECT * FROM labis_store")
    fun getAll(): Flow<List<StoreEntity>>

    @Query("SELECT * FROM labis_store WHERE id = :id")
    suspend fun get(id: Long): StoreEntity

    @Query("DELETE FROM labis_store WHERE id = :id")
    suspend fun delete(id: Long)

}