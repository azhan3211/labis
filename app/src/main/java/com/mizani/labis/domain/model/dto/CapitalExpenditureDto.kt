package com.mizani.labis.domain.model.dto

import java.util.Date

data class CapitalExpenditureDto(
    val totalPrice: Int,
    val data: List<CapitalExpenditureDataDto>
)

data class CapitalExpenditureDataDto(
    val id: Int = 0,
    val date: Date = Date(),
    val description: String = "",
    val price: Int = 0
)