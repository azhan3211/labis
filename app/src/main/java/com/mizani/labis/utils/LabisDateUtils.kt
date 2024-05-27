package com.mizani.labis.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class LabisDateUtils {
    companion object {

        private val dateFormat = SimpleDateFormat("dd MMM yyyy")

        private fun getCalendar(date: Date = Date()): Calendar {
            val instance = Calendar.getInstance()
            instance.time = date
            return instance
        }

        fun getCurrentTime(): Date {
            return getCalendar().time
        }

        fun getDateStartOfDay(date: Date = Date()): Date {
            val calendar = getCalendar(date)
            calendar.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                0,
                0,
                0
            )

            return calendar.time
        }

        fun getDateEndOfDay(date: Date = Date()): Date {
            val calendar = getCalendar(date)
            calendar.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                23,
                59,
                59
            )

            return calendar.time
        }

        fun Date.toReadableView(
            pattern: String = "dd MMM yyyy",
        ): String {
            return SimpleDateFormat(pattern).format(this)
        }

        fun Long.toReadableView(
            pattern: String = "dd MMM yyyy"
        ): String {
            return SimpleDateFormat(pattern).format(Date(this))
        }
    }
}