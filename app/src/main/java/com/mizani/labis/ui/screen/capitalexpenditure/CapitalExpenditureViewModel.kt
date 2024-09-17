package com.mizani.labis.ui.screen.capitalexpenditure

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mizani.labis.domain.model.dto.CapitalExpenditureCreateDto
import com.mizani.labis.domain.model.dto.CapitalExpenditureDataDto
import com.mizani.labis.domain.model.dto.Result
import com.mizani.labis.domain.repository.CapitalExpenditureRepository
import com.mizani.labis.domain.repository.PreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class CapitalExpenditureViewModel @Inject constructor(
    private val expenditureRepository: CapitalExpenditureRepository,
    private val preferenceRepository: PreferenceRepository
) : ViewModel() {

    private val _isSuccessDeleteCapitalExpenditure = mutableStateOf(false)
    val isSuccessDeleteCapitalExpenditure: State<Boolean> get() = _isSuccessDeleteCapitalExpenditure

    private val _isSuccessCreateCapitalExpenditure = mutableStateOf(false)
    val isSuccessCreateCapitalExpenditure: State<Boolean> get() = _isSuccessCreateCapitalExpenditure

    private val _capitalExpendituresDto = mutableStateListOf<CapitalExpenditureDataDto>()
    val capitalExpendituresDto: SnapshotStateList<CapitalExpenditureDataDto> get() = _capitalExpendituresDto

    private val _totalCapitalExpenditurePrice = mutableStateOf(0)
    val totalCapitalExpenditurePrice: State<Int> get() = _totalCapitalExpenditurePrice

    private val _errorCapitalExpenditure = mutableStateOf("")
    val errorCapitalExpenditure: State<String> get() = _errorCapitalExpenditure

    private val _isDataChanged = mutableStateOf(false)
    val isDataChanged: State<Boolean> get() = _isDataChanged

    fun getCapitalExpenditure(date: Date = Date()) {
        viewModelScope.launch {
            val result = expenditureRepository.getCapitalExpenditures(
                storeId = preferenceRepository.getSelectedStoreId().toInt(),
                date = date
            )
            when (result) {
                is Result.Success -> {
                    _capitalExpendituresDto.clear()
                    _capitalExpendituresDto.addAll(result.data.data)
                    calculateTotal()
                }
                is Result.Error -> {
                    _errorCapitalExpenditure.value = result.error.message
                }
            }
        }
    }

    private fun calculateTotal() {
        _totalCapitalExpenditurePrice.value = 0

        _capitalExpendituresDto.forEach {
            _totalCapitalExpenditurePrice.value += it.price
        }
    }

    fun createCapitalExpenditure(capitalExpenditureCreateDto: List<CapitalExpenditureCreateDto>, date: Date = Date()) {
        viewModelScope.launch {
            val result = expenditureRepository.createCapitalExpenditure(
                storeId = preferenceRepository.getSelectedStoreId().toInt(),
                date = date,
                capitalExpenditureCreateDto = capitalExpenditureCreateDto
            )
            when (result) {
                is Result.Success -> {
                    _isSuccessCreateCapitalExpenditure.value = true
                    _isDataChanged.value = true
                }
                is Result.Error -> {
                    Log.e("error capital ep", result.error.message)
                }
            }
        }
    }

    fun deleteCapitalExpenditure(id: Int) {
        viewModelScope.launch {
            val result = expenditureRepository.deleteCapitalExpenditure(
                storeId = preferenceRepository.getSelectedStoreId().toInt(),
                id = id
            )
            when(result) {
                is Result.Success -> {
                    _isSuccessDeleteCapitalExpenditure.value = true
                    val updatedData = _capitalExpendituresDto.filter { it.id != id }
                    _capitalExpendituresDto.clear()
                    _capitalExpendituresDto.addAll(updatedData)
                    calculateTotal()
                    _isDataChanged.value = true
                }
                is Result.Error -> {
                    _errorCapitalExpenditure.value = result.error.message
                }
            }
        }
    }

    fun resetSuccessCreateCapitalExpenditure() {
        _isSuccessCreateCapitalExpenditure.value = false
    }

    fun resetSuccessDeleteCapitalExpenditure() {
        _isSuccessDeleteCapitalExpenditure.value = false
    }

    fun resetError() {
        _errorCapitalExpenditure.value = ""
    }

}