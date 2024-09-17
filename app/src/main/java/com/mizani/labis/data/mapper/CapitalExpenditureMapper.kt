package com.mizani.labis.data.mapper

import com.mizani.labis.data.remote.expenditure.CapitalExpenditureDataResponse
import com.mizani.labis.data.remote.expenditure.CapitalExpenditureResponse
import com.mizani.labis.data.request.capitalexpenditure.CapitalExpenditureCreateRequest
import com.mizani.labis.domain.model.dto.CapitalExpenditureCreateDto
import com.mizani.labis.domain.model.dto.CapitalExpenditureDataDto
import com.mizani.labis.domain.model.dto.CapitalExpenditureDto
import com.mizani.labis.utils.LabisDateUtils.Companion.BACK_END_FORMAT
import com.mizani.labis.utils.LabisDateUtils.Companion.toDate
import com.mizani.labis.utils.LabisDateUtils.Companion.toReadableView
import com.mizani.labis.utils.StringUtils.toCurrency
import java.util.Date

object CapitalExpenditureMapper {

    fun List<CapitalExpenditureCreateDto>.toCreateRequest(storeId: Int, date: Date): CapitalExpenditureCreateRequest {
        var description = ""
        var price = 0
        forEachIndexed { index, data ->
            if (index != 0) {
                description += "\n"
            }
            description += "${data.name} ${data.price.toCurrency()}"
            price += data.price
        }
        return CapitalExpenditureCreateRequest(
            date = date,
            description = description,
            price = price,
            storeId = storeId
        )
    }

    fun CapitalExpenditureCreateRequest.toMapRequest(): HashMap<String, String> {
        val map = hashMapOf<String, String>()
        map["store_id"] = storeId.toString()
        if (date != null) {
            map["date"] = date.toReadableView("yyyy-MM-dd")
        }
        map["description"] = description
        map["price"] = price.toString()
        return map
    }

    fun CapitalExpenditureResponse.toDto(): CapitalExpenditureDto {
        return CapitalExpenditureDto(
            totalPrice = availableCapital,
            data = data.toDto()
        )
    }

    fun List<CapitalExpenditureDataResponse>.toDto(): List<CapitalExpenditureDataDto> {
        return map {
            it.toDto()
        }
    }

    fun CapitalExpenditureDataResponse.toDto(): CapitalExpenditureDataDto {
        return CapitalExpenditureDataDto(
            id = id,
            date = date.toDate(BACK_END_FORMAT),
            description = description,
            price = price,
        )
    }

}