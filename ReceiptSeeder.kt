package com.example.budgetapplication.data.databasetest

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateTimeHelper {

    fun create(
        month: Int,
        day: Int,
        year: Int,
        hour: Int = 0,
        minute: Int = 0,
        second: Int = 0
    ): Date {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, day, hour, minute, second)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }
    fun formatDate(timeMillis: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date(timeMillis))
    }

    // Converts a Long timestamp to "HH:mm:ss" string
    fun formatTime(timeMillis: Long): String {
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return sdf.format(Date(timeMillis))
    }

}
