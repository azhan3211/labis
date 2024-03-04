package com.mizani.labis.data.repository

import com.mizani.labis.data.dao.ProductCategoryDao
import com.mizani.labis.data.dao.ProductDao
import com.mizani.labis.data.dto.store.ProductCategoryDto
import com.mizani.labis.data.dto.store.ProductDto
import com.mizani.labis.data.dto.store.toProductCategoryEntity
import com.mizani.labis.data.dto.store.toProductEntity
import com.mizani.labis.data.entity.toProductCategoryDto
import com.mizani.labis.data.entity.toProductDto
import com.mizani.labis.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productDao: ProductDao,
    private val productCategoryDao: ProductCategoryDao
) : ProductRepository {
    override suspend fun getAll(storeId: Long): Flow<List<ProductDto>> {
        return flow {
            productDao.getAll(storeId).collect {
                emit(
                    it.map { productEntity ->
                        productEntity.toProductDto()
                    }
                )
            }
        }
    }

    override suspend fun get(id: Long): ProductDto {
        return productDao.get(id).toProductDto()
    }

    override suspend fun saveProduct(productDto: ProductDto) {
        productDao.insert(productDto.toProductEntity())
    }

    override suspend fun delete(id: Long) {
        productDao.delete(id)
    }

    override suspend fun getProductCategoryAll(storeId: Long): Flow<List<ProductCategoryDto>> {
        return flow {
            productCategoryDao.getAll(storeId).collect {
                emit(
                    it.map { productCategoryEntity ->
                        productCategoryEntity.toProductCategoryDto()
                    }
                )
            }
        }
    }

    override suspend fun saveProductCategory(productCategoryDto: ProductCategoryDto): Long {
        return productCategoryDao.insert(productCategoryEntity = productCategoryDto.toProductCategoryEntity())
    }

    override suspend fun deleteProductCategory(id: Long) {
        productCategoryDao.delete(id)
    }

    override suspend fun getProductCategory(id: Long): ProductCategoryDto {
        return productCategoryDao.get(id).toProductCategoryDto()
    }
}