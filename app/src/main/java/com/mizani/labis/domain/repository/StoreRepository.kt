package com.mizani.labis.domain.repository

import com.mizani.labis.data.dto.store.StoreDto
import kotlinx.coroutines.flow.Flow

interface StoreRepository {

    suspend fun getStores(): Flow<List<StoreDto>>
    suspend fun getStore(id: Long): StoreDto
    suspend fun saveStore(storeDto: StoreDto)
    suspend fun deleteStore(id: Long)

}