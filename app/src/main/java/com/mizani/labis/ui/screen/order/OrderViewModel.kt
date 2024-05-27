package com.mizani.labis.ui.screen.order

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mizani.labis.domain.model.dto.OrderDto
import com.mizani.labis.domain.model.dto.OrdersDto
import com.mizani.labis.domain.repository.OrderRepository
import com.mizani.labis.domain.repository.PreferenceRepository
import com.mizani.labis.utils.LabisDateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val preferenceRepository: PreferenceRepository
) : ViewModel() {

    val orders: List<OrdersDto> get() = _orders
    private val _orders: ArrayList<OrdersDto> = arrayListOf()

    val totalPrice: State<Int> get() = _totalPrice
    private val _totalPrice = mutableStateOf(0)

    val totalOrder: State<Int> get() = _totalOrder
    private val _totalOrder = mutableStateOf(0)

    val isTotalOrderZero: State<Boolean> get() = _isTotalOrderZero
    private val _isTotalOrderZero = mutableStateOf(false)

    val isSaved: State<Boolean> get() = _isSaved
    private val _isSaved = mutableStateOf(false)

    fun setOrders(orders: List<OrdersDto>) {
        this._orders.clear()
        this._orders.addAll(orders)
        calculateTotalPrice()
    }

    fun onOrderInc(orderDto: OrdersDto) {
        val currentOrder = _orders.find { it.id == orderDto.id }
        if (currentOrder == null) {
            _orders.add(orderDto)
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

    fun onOrderDec(orderDto: OrdersDto) {
        val currentOrder = _orders.find { it.id == orderDto.id }
        if (currentOrder != null) {
            val newOrder = currentOrder.copy(count = currentOrder.count - 1)
            if (newOrder.count == 0) {
                val ordersTemp = arrayListOf<OrdersDto>()
                ordersTemp.addAll(_orders)
                for (index in 0 until ordersTemp.size) {
                    if (ordersTemp[index].id == newOrder.id) {
                        ordersTemp.removeAt(index)
                        break
                    }
                }
                _orders.clear()
                _orders.addAll(ordersTemp)
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
        var totalOrderTemp = 0
        _orders.forEach {
            totalPriceTemp += (it.price * it.count)
            totalOrderTemp += it.count
        }
        _totalPrice.value = totalPriceTemp
        _totalOrder.value = totalOrderTemp

        if (_totalOrder.value <= 0) {
            _isTotalOrderZero.value = true
        }
    }

    fun onSave(orderDto: OrderDto) {
        val newOrder = orderDto.copy(
            storeId = preferenceRepository.getSelectedStoreId(),
            dateTime = LabisDateUtils.getCurrentTime(),
            orders = _orders
        )
        viewModelScope.launch {
            orderRepository.saveOrder(
                newOrder
            )
            _isSaved.value = true
        }
    }

}