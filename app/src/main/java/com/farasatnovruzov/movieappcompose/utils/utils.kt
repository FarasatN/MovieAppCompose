package com.farasatnovruzov.movieappcompose.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDate(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat(
//        "EEE, d MMM yyyy, hh:mm aaa",
        "EEE, dd MMM hh:mm",
//        "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
        Locale.getDefault()
    )
    return format.format(date)

}

fun fahrenheitToCelsius(fahrenheit: Double): String {
    return ((fahrenheit - 32) * 5 / 9).toInt().toString()
}