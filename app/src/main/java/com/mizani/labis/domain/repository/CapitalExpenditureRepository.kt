package com.mizani.labis.domain.repository

import com.mizani.labis.domain.model.dto.CapitalExpenditureCreateDto
import com.mizani.labis.domain.model.dto.CapitalExpenditureDto
import com.mizani.labis.domain.model.dto.ErrorDto
import com.mizani.labis.domain.model.dto.Result
import java.util.Date

interface CapitalExpenditureRepository {

    suspend fun getCapitalExpenditures(storeId: Int, date: Date = Date()): Result<CapitalExpenditureDto, ErrorDto>
    suspend fun createCapitalExpenditure(storeId: Int, date: Date = Date(), capitalExpenditureCreateDto: List<CapitalExpenditureCreateDto>): Result<String, ErrorDto>
    suspend fun updateCapitalExpenditure(capitalExpenditureDto: CapitalExpenditureDto)

    suspend fun deleteCapitalExpenditure(storeId: Int, id: Int): Result<String, ErrorDto>

}