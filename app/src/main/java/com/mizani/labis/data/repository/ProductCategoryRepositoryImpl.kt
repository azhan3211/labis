package com.mizani.labis.data.repository

import com.mizani.labis.data.local.ProductCategoryDao
import com.mizani.labis.data.remote.product.ProductService
import com.mizani.labis.data.remote.product.ProductTypeResponse.Companion.toDto
import com.mizani.labis.domain.model.dto.ErrorDto
import com.mizani.labis.domain.model.dto.ProductCategoryDto
import com.mizani.labis.domain.model.dto.Result
import com.mizani.labis.domain.model.dto.toProductCategoryEntity
import com.mizani.labis.domain.model.entity.ProductCategoryEntity
import com.mizani.labis.domain.model.entity.toProductCategoryDto
import com.mizani.labis.domain.repository.ProductCategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductCategoryRepositoryImpl @Inject constructor(
    private val productService: ProductService,
    private val productCategoryDao: ProductCategoryDao
) : ProductCategoryRepository {
    override suspend fun getCategories(storeId: Int): Result<List<ProductCategoryDto>, ErrorDto> {
        return try {
            val data = productService.getProductTypes(storeId)
            if (data.isSuccessful) {
                val categories = data.body()?.data?.map { type ->
                    type.toDto()
                }.orEmpty()
                Result.Success(categories)
            } else {
                Result.Error(ErrorDto(data.errorBody().toString()))
            }
        } catch (e: Exception) {
            Result.Error(ErrorDto(e.message.toString()))
        }
    }

    override suspend fun getCategoriesOffline(storeId: Long): Flow<List<ProductCategoryDto>> {
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

    override suspend fun delete(id: Long) {
        productCategoryDao.delete(id)
    }

    override suspend fun save(productCategoryDto: ProductCategoryDto): Long {
        return productCategoryDao.insert(productCategoryDto.toProductCategoryEntity())
    }

    override suspend fun getCategory(id: Long): ProductCategoryDto {
        return productCategoryDao.get(id).toProductCategoryDto()
    }


}