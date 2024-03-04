package com.mizani.labis.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mizani.labis.domain.model.dto.ProductCategoryDto
import com.mizani.labis.domain.model.dto.ProductDto
import com.mizani.labis.domain.model.dto.StoreDto
import com.mizani.labis.domain.repository.PreferenceRepository
import com.mizani.labis.domain.repository.ProductRepository
import com.mizani.labis.domain.repository.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val storeRepository: StoreRepository,
    private val preferenceRepository: PreferenceRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    val products: SnapshotStateList<ProductDto> get() = _products
    private val _products = mutableStateListOf<ProductDto>()

    val categries: SnapshotStateList<ProductCategoryDto> get() = _categories
    private val _categories = mutableStateListOf<ProductCategoryDto>()

    val selectedStore: State<StoreDto?> get() = _selectedStore
    private val _selectedStore = mutableStateOf<StoreDto?>(null)

    fun getProducts() {
        viewModelScope.launch {
            productRepository.getAll(getSelectedStoreId()).collect {
                _products.clear()
                _products.addAll(it)
            }
        }
    }

    fun getProductCategories() {
        viewModelScope.launch {
            productRepository.getProductCategoryAll(getSelectedStoreId()).collect {
                _categories.clear()
                _categories.addAll(it)
            }
        }
    }

    fun getSelectedStore() {
        viewModelScope.launch {
            val selectedStoreId = getSelectedStoreId()
            if (selectedStoreId != 0L) {
                _selectedStore.value = storeRepository.getStore(getSelectedStoreId())
            }
        }
    }

    private fun getSelectedStoreId(): Long {
        return preferenceRepository.getSelectedStoreId()
    }

}