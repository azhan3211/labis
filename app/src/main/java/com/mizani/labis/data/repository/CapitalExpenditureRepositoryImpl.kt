package com.mizani.labis.data.repository

import android.util.Log
import com.mizani.labis.data.mapper.CapitalExpenditureMapper.toCreateRequest
import com.mizani.labis.data.mapper.CapitalExpenditureMapper.toDto
import com.mizani.labis.data.mapper.CapitalExpenditureMapper.toMapRequest
import com.mizani.labis.data.remote.expenditure.CapitalExpenditureResponse
import com.mizani.labis.data.remote.expenditure.CapitalExpenditureService
import com.mizani.labis.domain.model.dto.CapitalExpenditureCreateDto
import com.mizani.labis.domain.model.dto.CapitalExpenditureDto
import com.mizani.labis.domain.model.dto.ErrorDto
import com.mizani.labis.domain.model.dto.Result
import com.mizani.labis.domain.repository.CapitalExpenditureRepository
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class CapitalExpenditureRepositoryImpl @Inject constructor(
    private val expenditureService: CapitalExpenditureService
) : CapitalExpenditureRepository {
    override suspend fun getCapitalExpenditures(
        storeId: Int,
        date: Date
    ): Result<CapitalExpenditureDto, ErrorDto> {
        return try {
            val calendar = Calendar.getInstance()
            calendar.time = date
            val request = hashMapOf<String, String>()
            request["store_id"] = storeId.toString()
            request["year"] = calendar.get(Calendar.YEAR).toString()
            request["month"] = calendar.get(Calendar.MONTH).inc().toString()
            request["date"] = calendar.get(Calendar.DAY_OF_MONTH).toString()
            val response = expenditureService.getCapitalExpenditure(request)
            if (response.isSuccessful) {
                val data = response.body()?.data ?: CapitalExpenditureResponse()
                Result.Success(data.toDto())
            } else {
                Result.Error(ErrorDto(message = response.errorBody().toString()))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(ErrorDto(message = e.message.toString()))
        }
    }

    override suspend fun createCapitalExpenditure(
        storeId: Int,
        date: Date,
        capitalExpenditureCreateDto: List<CapitalExpenditureCreateDto>
    ): Result<String, ErrorDto> {
        return try {
            val request = capitalExpenditureCreateDto.toCreateRequest(storeId, date)
            val response = expenditureService.createCapitalExpenditure(request.toMapRequest())
            if (response.isSuccessful) {
                Result.Success("Success create capital expenditure")
            } else {
                Result.Error(ErrorDto(message = response.errorBody().toString()))
            }
        } catch (e: Exception) {
            Result.Error(ErrorDto(message = e.message.toString()))
        }
    }

    override suspend fun updateCapitalExpenditure(capitalExpenditureDto: CapitalExpenditureDto) {

    }

    override suspend fun deleteCapitalExpenditure(storeId: Int, id: Int): Result<String, ErrorDto> {
        return try {
            val response = expenditureService.deleteCapitalExpenditure(storeId, id)
            if (response.isSuccessful) {
                Result.Success("Delete Success")
            } else {
                Result.Error(ErrorDto(response.errorBody().toString()))
            }
        } catch (e: Exception) {
            Result.Error(ErrorDto(e.message.toString()))
        }
    }
}