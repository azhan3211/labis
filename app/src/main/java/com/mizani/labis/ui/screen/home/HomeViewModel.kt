package com.mizani.labis.ui.screen.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mizani.labis.domain.model.dto.OrderDto
import com.mizani.labis.domain.model.dto.OrdersDto
import com.mizani.labis.domain.model.dto.ProductCategoryDto
import com.mizani.labis.domain.model.dto.ProductDto
import com.mizani.labis.domain.model.dto.StoreDto
import com.mizani.labis.domain.model.dto.toOrderDto
import com.mizani.labis.domain.repository.OrderRepository
import com.mizani.labis.domain.repository.PreferenceRepository
import com.mizani.labis.domain.repository.ProductCategoryRepository
import com.mizani.labis.domain.repository.ProductRepository
import com.mizani.labis.domain.repository.StoreRepository
import com.mizani.labis.utils.LabisDateUtils.Companion.toReadableView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val storeRepository: StoreRepository,
    private val preferenceRepository: PreferenceRepository,
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository,
    private val productCategoryRepository: ProductCategoryRepository
) : ViewModel() {

    val products: SnapshotStateList<ProductDto> get() = _products
    private val _products = mutableStateListOf<ProductDto>()

    val categories: SnapshotStateList<ProductCategoryDto> get() = _categories
    private val _categories = mutableStateListOf<ProductCategoryDto>()

    val selectedStore: State<StoreDto?> get() = _selectedStore
    private val _selectedStore = mutableStateOf<StoreDto?>(null)

    val orders: List<OrdersDto> get() = _orders
    private val _orders: ArrayList<OrdersDto> = arrayListOf()

    val totalPrice: State<Int> get() = _totalPrice
    private val _totalPrice = mutableStateOf(0)

    val orderPaidSaved: List<OrderDto> get() = _orderPaidSaved
    private val _orderPaidSaved = mutableStateListOf<OrderDto>()

    val orderUnpaidSaved: List<OrderDto> get() = _orderUnpaidSaved
    private val _orderUnpaidSaved = mutableStateListOf<OrderDto>()

    val paidSale: State<Int> get() = _paidSale
    private val _paidSale = mutableStateOf(0)
    val unpaidSale: State<Int> get() = _unpaidSale
    private val _unpaidSale = mutableStateOf(0)

    val selectedStartDate: State<Date> get() = _selectedStartDate
    private val _selectedStartDate = mutableStateOf(Date())

    val selectedEndDate: State<Date> get() = _selectedEndDate
    private val _selectedEndDate = mutableStateOf(Date())

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
            productCategoryRepository.getCategories(getSelectedStoreId()).collect {
                _categories.clear()
                _categories.addAll(it)
            }
        }
    }

    fun getSelectedStore() {
        viewModelScope.launch {
            val selectedStoreId = getSelectedStoreId()
            if (selectedStoreId != 0L) {
                _selectedStore.value = storeRepository.getStore(selectedStoreId)
            }
        }
    }

    private fun getSelectedStoreId(): Long {
        return preferenceRepository.getSelectedStoreId()
    }

    fun onOrderInc(productDto: ProductDto) {
        val currentOrder = _orders.find { it.id == productDto.id }
        if (currentOrder == null) {
            _orders.add(productDto.toOrderDto().copy(count = 1))
        } else {
            val newOrder = currentOrder.copy(count = currentOrder.count + 1)
            _orders.map {
                if (it.id == newOrder.id) {
                    it.count = newOrder.count
                }
            }
        }
        calculateTotalPrice()
    }

    fun onOrderDec(productDto: ProductDto) {
        val currentOrder = _orders.find { it.id == productDto.id }
        if (currentOrder != null) {
            val newOrder = currentOrder.copy(count = currentOrder.count - 1)
            if (newOrder.count == 0) {
                _orders.forEachIndexed { index, data ->
                    if (data.id == newOrder.id) {
                        _orders.removeAt(index)
                    }
                }
            } else {
                _orders.map {
                    if (it.id == newOrder.id) {
                        it.count = newOrder.count
                    }
                }
            }
        }
        calculateTotalPrice()
    }

    private fun calculateTotalPrice() {
        var totalPriceTemp = 0
        _orders.forEach {
            totalPriceTemp += (it.price * it.count)
        }
        _totalPrice.value = totalPriceTemp
    }

    fun getOrder(
        startDate: Date = Date(),
        endDate: Date = Date()
    ) {
        viewModelScope.launch {
            orderRepository.getOrderPaid(
                preferenceRepository.getSelectedStoreId(),
                startDate = startDate,
                endDate = endDate
            ).collect {
                _orderPaidSaved.clear()
                _orderPaidSaved.addAll(it)
                calculateSale()
            }
        }
    }

    fun getOrderUnpaid(
        startDate: Date = Date(),
        endDate: Date = Date()
    ) {
        viewModelScope.launch {
            orderRepository.getOrderUnpaid(
                preferenceRepository.getSelectedStoreId(),
                startDate = startDate,
                endDate = endDate
            ).collect {
                _orderUnpaidSaved.clear()
                _orderUnpaidSaved.addAll(it)
                calculateUnpaidSale()
            }
        }
    }

    private fun calculateSale() {
        var totalSale = 0

        _orderPaidSaved.forEach { order ->
            if (order.isPayLater().not()) {
                order.orders.forEach {
                    totalSale += (it.count * it.price)
                }
            }
        }

        _paidSale.value = totalSale
    }

    private fun calculateUnpaidSale() {
        var totalSaleUnpaid = 0

        _orderUnpaidSaved.forEach {order ->
            if (order.status == OrderDto.UNPAID) {
                order.orders.forEach {
                    totalSaleUnpaid += (it.count * it.price)
                }
            }
        }

        _unpaidSale.value = totalSaleUnpaid
    }

    fun setSelectedDate(startDate: Date, endDate: Date) {
        _selectedStartDate.value = startDate
        _selectedEndDate.value = endDate
    }

}