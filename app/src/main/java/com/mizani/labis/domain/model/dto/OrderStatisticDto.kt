package com.mizani.labis.domain.model.dto

data class OrderStatisticDto(
    val capital: Int = 0,
    val profit: Int = 0,
    val availableCapital: Int = 0,
    val expenditure: Int = 0,
    val debt: Int = 0,
    val salary: Int = 0,
    val startDate: String = "",
    val endDate: String = ""
) {
    fun getOmzet() = capital + profit
}