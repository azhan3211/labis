package com.mizani.labis.ui.screen.product

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mizani.labis.domain.model.dto.ProductCategoryDto
import com.mizani.labis.domain.model.dto.ProductDto
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

    fun getProducts(storeId: Long) {
        viewModelScope.launch {
            productRepository.getAll(storeId).collect {
                _products.clear()
                _products.addAll(it)
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

    fun getProductCategory(storeId: Long) {
        viewModelScope.launch {
            productCategoryRepository.getCategories(storeId).collect {
                _productCategories.clear()
                _productCategories.addAll(it)
            }
        }
    }

    fun saveCategoryAndProduct(
        productCategoryDto: ProductCategoryDto,
        productDto: ProductDto
    ) {
        viewModelScope.launch {
            val categoryId = productCategoryRepository.save(productCategoryDto)
            val product = productDto.copy(
                categoryId = categoryId
            )
            saveProduct(productDto = product)
        }
    }

    fun setSelectProduct(productDto: ProductDto) {
        _selectedProduct.value = productDto
    }
}