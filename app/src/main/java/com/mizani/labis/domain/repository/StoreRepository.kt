package com.mizani.labis.domain.repository

import com.mizani.labis.domain.model.dto.StoreDto
import kotlinx.coroutines.flow.Flow

interface StoreRepository {

    suspend fun getStores(): List<StoreDto>
    suspend fun getStoresOffline(): Flow<List<StoreDto>>
    suspend fun getStoreOffline(id: Long): StoreDto
    suspend fun getStore(id: Int): StoreDto
    suspend fun saveStore(storeDto: StoreDto)
    suspend fun deleteStore(id: Long)
    suspend fun updateStore(storeDto: StoreDto)

}