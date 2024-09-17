package com.mizani.labis.data.repository

import com.mizani.labis.data.local.StoreDao
import com.mizani.labis.data.remote.store.StoreDataResponse.Companion.toDto
import com.mizani.labis.data.remote.store.StoreService
import com.mizani.labis.data.request.store.StoreCreateRequest
import com.mizani.labis.domain.model.dto.StoreDto
import com.mizani.labis.domain.model.dto.toStoreEntity
import com.mizani.labis.domain.model.entity.toStoreDto
import com.mizani.labis.domain.repository.StoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(
    private val storeService: StoreService,
    private val storeDao: StoreDao
) : StoreRepository {

    override suspend fun getStores(): List<StoreDto> {
        return storeService.getStores().stores.map { store -> store.toDto() }
    }

    override suspend fun getStoresOffline(): Flow<List<StoreDto>> {
        return flow {
            storeDao.getAll().collect {
                emit(
                    it.map { storeEntity ->
                        storeEntity.toStoreDto()
                    }
                )
            }
        }
    }

    override suspend fun getStoreOffline(id: Long): StoreDto {
        return storeDao.get(id).toStoreDto()
    }

    override suspend fun getStore(id: Int): StoreDto {
        return storeService.getStore(id).toDto()
    }

    override suspend fun saveStore(storeDto: StoreDto) {
        storeService.createStore(
            StoreCreateRequest(
                name = storeDto.name,
                address = storeDto.address
            )
        )
        storeDao.insert(storeDto.toStoreEntity())
    }

    override suspend fun deleteStore(id: Long) {
        storeDao.delete(id)
    }

    override suspend fun updateStore(storeDto: StoreDto) {
        storeService.updateStore(
            StoreCreateRequest(
                id = storeDto.id.toInt(),
                name = storeDto.name,
                address = storeDto.address
            )
        )
    }
}