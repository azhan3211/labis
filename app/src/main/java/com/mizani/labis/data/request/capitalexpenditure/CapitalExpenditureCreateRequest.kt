package com.mizani.labis.data.request.capitalexpenditure

import java.util.Date

data class CapitalExpenditureCreateRequest(
    val storeId: Int,
    val description: String,
    val price: Int,
    val date: Date?,
)