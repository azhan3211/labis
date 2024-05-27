package com.mizani.labis.domain.repository

import com.mizani.labis.domain.model.dto.ProductCategoryDto
import com.mizani.labis.domain.model.dto.ProductDto
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun getAll(storeId: Long): Flow<List<ProductDto>>
    suspend fun get(id: Long): ProductDto
    suspend fun saveProduct(productDto: ProductDto)
    suspend fun delete(id: Long)

}