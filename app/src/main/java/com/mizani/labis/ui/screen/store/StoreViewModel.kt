package com.mizani.labis.ui.screen.store

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mizani.labis.domain.model.dto.StoreDto
import com.mizani.labis.domain.repository.PreferenceRepository
import com.mizani.labis.domain.repository.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val storeRepository: StoreRepository,
    private val preferenceRepository: PreferenceRepository
) : ViewModel() {

    val store: SnapshotStateList<StoreDto> get() = _stores
    private val _stores = mutableStateListOf<StoreDto>()

    val selectedStore: State<StoreDto?> get() = _selectedStore
    private val _selectedStore = mutableStateOf<StoreDto?>(null)

    fun getStores() {
        viewModelScope.launch {
            _stores.clear()
            _stores.addAll(storeRepository.getStores())
        }
    }

    fun deleteStore(storeDto: StoreDto) {
        viewModelScope.launch {
            storeRepository.deleteStore(storeDto.id)
        }
    }

    fun saveStore(storeDto: StoreDto) {
        viewModelScope.launch {
            storeRepository.saveStore(storeDto)
        }
    }

    fun updateStore(storeDto: StoreDto) {
        viewModelScope.launch {
            storeRepository.updateStore(storeDto)
        }
    }

    fun getSelectedStore(id: Int) {
        viewModelScope.launch {
            val data = storeRepository.getStore(id)
            _selectedStore.value = storeRepository.getStore(data.id.toInt())
        }
    }

    fun getSelected(storeDto: StoreDto) {
        viewModelScope.launch {
            _selectedStore.value = storeRepository.getStore(storeDto.id.toInt())
        }
    }

    fun setSelectedStore(id: Long) {
        preferenceRepository.setSelectedStoreId(id)
    }

}