package com.mizani.labis.ui.screen.report

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mizani.labis.domain.model.dto.OrderDto
import com.mizani.labis.domain.repository.OrderRepository
import com.mizani.labis.domain.repository.PreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val preferenceRepository: PreferenceRepository
) : ViewModel() {

    val orderPaidSaved: List<OrderDto> get() = _orderPaidSaved
    private val _orderPaidSaved = mutableStateListOf<OrderDto>()

    fun getOrder(
        startDate: Date = Date(),
        endDate: Date = Date()
    ) {
        viewModelScope.launch {
            orderRepository.getOrders(
                preferenceRepository.getSelectedStoreId(),
                startDate = startDate,
                endDate = endDate
            ).collect {
                _orderPaidSaved.clear()
                _orderPaidSaved.addAll(it)
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
                _orderPaidSaved.clear()
                _orderPaidSaved.addAll(it)
            }
        }
    }

    fun getOrderPaid(
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
            }
        }
    }

    fun paidDebt(orderDto: OrderDto) {
        viewModelScope.launch {
            val updateOrder = orderDto.copy(
                status = OrderDto.PAID
            )
            orderRepository.paidDebt(updateOrder)
        }
    }
}