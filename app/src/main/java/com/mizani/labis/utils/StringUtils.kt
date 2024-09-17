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

    fun String.toNumberFormat(): String {
        val numberFormat = DecimalFormat("###,###,###")
        if (isEmpty()) {
            return "0"
        }
        val number = this.replace(".", "").toInt()
        return numberFormat.format(number).replace(",", ".")
    }

}