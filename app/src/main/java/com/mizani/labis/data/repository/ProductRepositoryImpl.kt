package com.mizani.labis.data.repository

import com.mizani.labis.data.local.ProductDao
import com.mizani.labis.data.remote.product.ProductResponse.Companion.toDto
import com.mizani.labis.data.remote.product.ProductService
import com.mizani.labis.data.request.product.ProductCreateRequest
import com.mizani.labis.domain.model.dto.ErrorDto
import com.mizani.labis.domain.model.dto.ProductDto
import com.mizani.labis.domain.model.dto.Result
import com.mizani.labis.domain.model.dto.toUpdateRequest
import com.mizani.labis.domain.model.entity.toProductDto
import com.mizani.labis.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productDao: ProductDao,
    private val productService: ProductService
) : ProductRepository {
    override suspend fun getAll(storeId: Int): Result<List<ProductDto>, ErrorDto> {
        return try {
            val response = productService.getProducts(storeId)
            if (response.isSuccessful) {
                val data = response.body()?.data?.map {
                    it.toDto()
                }.orEmpty()
                Result.Success(data)
            } else {
                Result.Error(ErrorDto(response.errorBody().toString()))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(ErrorDto(e.message.toString()))
        }
    }

    override suspend fun getAllOffline(storeId: Long): Flow<List<ProductDto>> {
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

    override suspend fun get(id: Int): Result<ProductDto, ErrorDto> {
        val response = productService.getProduct(id)
        return if (response.isSuccessful) {
            val productDto = response.body()?.data?.toDto()
            if (productDto == null) {
                Result.Success(ProductDto())
            } else {
                Result.Success(productDto)
            }
        } else {
            Result.Error(ErrorDto(response.errorBody().toString()))
        }
    }

    override suspend fun saveProduct(productDto: ProductDto) {
        try {
            val data = productService.createProduct(
                ProductCreateRequest(
                    name = productDto.name,
                    price = productDto.priceToSell,
                    profit = productDto.priceToSell - productDto.capital,
                    storeId = productDto.storeId.toInt(),
                    active = 1,
                    quantity = productDto.stock,
                    typeId = productDto.categoryId.toInt(),
                    typeName = productDto.categoryName
                )
            )
            if (data.isSuccessful) {

            } else {

            }
        } catch (e: Exception) {

        }
    }

    override suspend fun delete(id: Long) {
        productDao.delete(id)
    }

    override suspend fun updateProduct(
        productDto: ProductDto,
        currentProductDto: ProductDto
    ): Result<String, ErrorDto> {
        return try {
            val data = productService.updateProduct(productDto.toUpdateRequest(currentProductDto))
            if (data.isSuccessful) {
                Result.Success("success")
            } else {
                Result.Error(ErrorDto(data.errorBody().toString()))
            }
        } catch (e: Exception) {
            Result.Error(ErrorDto(e.message.toString()))
        }
    }
}