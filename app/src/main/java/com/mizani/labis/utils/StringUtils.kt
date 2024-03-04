package com.mizani.labis.utils

import java.text.DecimalFormat
import java.text.NumberFormat

object StringUtils {

    private val format = DecimalFormat("###,###,###,###")

    fun Int.toCurrency(currency: String = "Rp."): String {
        return "$currency${format.format(this).replace(",", ".")}"
    }

    fun Int.toDecimalFormat(): String {
        return "${format.format(this).replace(",", ".")}"
    }

}