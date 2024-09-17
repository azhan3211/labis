package com.mizani.labis.ui.screen.product

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mizani.labis.domain.model.dto.ProductCategoryDto
import com.mizani.labis.domain.model.dto.ProductDto
import com.mizani.labis.domain.model.dto.Result
import com.mizani.labis.domain.repository.ProductCategoryRepository
import com.mizani.labis.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val productCategoryRepository: ProductCategoryRepository
) : ViewModel() {

    val products: SnapshotStateList<ProductDto> get() = _products
    private val _products = mutableStateListOf<ProductDto>()

    val selectedProduct: State<ProductDto> get() = _selectedProduct
    private val _selectedProduct = mutableStateOf(ProductDto())
    val productCategories: SnapshotStateList<ProductCategoryDto> get() = _productCategories
    private val _productCategories = mutableStateListOf<ProductCategoryDto>()

    val productError: State<String> get() = _productError
    private val _productError = mutableStateOf("")

    fun getProducts(storeId: Long) {
        viewModelScope.launch {
            if (storeId.toInt() == 0) return@launch
            when (val data = productRepository.getAll(storeId.toInt())) {
                is Result.Success -> {
                    _products.clear()
                    _products.addAll(data.data)
                }
                is Result.Error -> {
                    _productError.value = data.error.message
                }
            }
        }
    }

    fun deleteProduct(productDto: ProductDto) {
        viewModelScope.launch {
            productRepository.delete(productDto.id)
        }
    }

    fun saveProduct(productDto: ProductDto) {
        viewModelScope.launch {
            productRepository.saveProduct(productDto)
        }
    }

    fun updateProduct(productDto: ProductDto, storeId: Long) {
        viewModelScope.launch {
            productRepository.updateProduct(productDto, selectedProduct.value)
            getProducts(storeId)
        }
    }

    fun getProductCategory(storeId: Long) {
        viewModelScope.launch {
            when (val data = productCategoryRepository.getCategories(storeId.toInt())) {
                is Result.Success -> {
                    _productCategories.clear()
                    _productCategories.addAll(data.data)
                }
                is Result.Error -> {

                }
            }
        }
    }

    fun saveCategoryAndProduct(
        productCategoryDto: ProductCategoryDto,
        productDto: ProductDto
    ) {
        viewModelScope.launch {
            val product = productDto.copy(
                categoryId = 0,
                categoryName = productCategoryDto.name
            )
            saveProduct(productDto = product)
        }
    }

    fun getProduct(id: Int) {
        viewModelScope.launch {
            when (val data = productRepository.get(id)) {
                is Result.Success -> {
                    _selectedProduct.value = data.data
                }
                is Result.Error -> {
                    _productError.value = data.error.message
                }
            }
        }
    }
}