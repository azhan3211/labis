package com.mizani.labis.domain.repository

import com.mizani.labis.data.dto.store.ProductCategoryDto
import com.mizani.labis.data.dto.store.ProductDto
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun getAll(storeId: Long): Flow<List<ProductDto>>
    suspend fun get(id: Long): ProductDto
    suspend fun saveProduct(productDto: ProductDto)
    suspend fun delete(id: Long)
    suspend fun getProductCategoryAll(storeId: Long): Flow<List<ProductCategoryDto>>
    suspend fun saveProductCategory(productCategoryDto: ProductCategoryDto): Long
    suspend fun deleteProductCategory(id: Long)
    suspend fun getProductCategory(id: Long): ProductCategoryDto

}