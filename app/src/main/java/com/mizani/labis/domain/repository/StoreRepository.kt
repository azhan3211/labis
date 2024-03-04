package com.mizani.labis.domain.repository

import com.mizani.labis.domain.model.dto.StoreDto
import kotlinx.coroutines.flow.Flow

interface StoreRepository {

    suspend fun getStores(): Flow<List<StoreDto>>
    suspend fun getStore(id: Long): StoreDto
    suspend fun saveStore(storeDto: StoreDto)
    suspend fun deleteStore(id: Long)

}