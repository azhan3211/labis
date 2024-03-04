package com.mizani.labis.data.repository

import com.mizani.labis.data.local.StoreDao
import com.mizani.labis.domain.model.dto.StoreDto
import com.mizani.labis.domain.model.dto.toStoreEntity
import com.mizani.labis.domain.model.entity.toStoreDto
import com.mizani.labis.domain.repository.StoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(
    private val storeDao: StoreDao
) : StoreRepository {
    override suspend fun getStores(): Flow<List<StoreDto>> {
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

    override suspend fun getStore(id: Long): StoreDto {
        return storeDao.get(id).toStoreDto()
    }

    override suspend fun saveStore(storeDto: StoreDto) {
        storeDao.insert(storeDto.toStoreEntity())
    }

    override suspend fun deleteStore(id: Long) {
        storeDao.delete(id)
    }
}