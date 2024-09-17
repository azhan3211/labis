package com.mizani.labis.domain.repository

import com.mizani.labis.domain.model.dto.ErrorDto
import com.mizani.labis.domain.model.dto.ProductCategoryDto
import com.mizani.labis.domain.model.dto.Result
import kotlinx.coroutines.flow.Flow

interface ProductCategoryRepository {

    suspend fun getCategories(storeId: Int): Result<List<ProductCategoryDto>, ErrorDto>

    suspend fun getCategoriesOffline(storeId: Long): Flow<List<ProductCategoryDto>>

    suspend fun getCategory(id: Long): ProductCategoryDto

    suspend fun delete(id: Long)

    suspend fun save(productCategoryDto: ProductCategoryDto): Long

}