package com.mizani.labis.domain.repository

import com.mizani.labis.domain.model.dto.ErrorDto
import com.mizani.labis.domain.model.dto.ProductDto
import com.mizani.labis.domain.model.dto.Result
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun getAll(storeId: Int): Result<List<ProductDto>, ErrorDto>
    suspend fun getAllOffline(storeId: Long): Flow<List<ProductDto>>
    suspend fun get(id: Int): Result<ProductDto, ErrorDto>
    suspend fun saveProduct(productDto: ProductDto)
    suspend fun delete(id: Long)
    suspend fun updateProduct(productDto: ProductDto, curretProductDto: ProductDto): Result<String, ErrorDto>

}