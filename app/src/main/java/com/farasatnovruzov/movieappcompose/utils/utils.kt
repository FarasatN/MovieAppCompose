package com.farasatnovruzov.movieappcompose.utils

import android.icu.text.DateFormat
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDate(time: Long): String {
    val date = Date(time * 1000L)
    val format = SimpleDateFormat(
//        "EEE, d MMM yyyy, hh:mm aaa",
//        "EEE, dd MMM hh:mm",
//        "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
        "EEE, dd MMM yy, HH:mm",
        Locale.getDefault()
    )
    return format.format(date)

}

fun fahrenheitToCelsius(fahrenheit: Double): String {
    return ((fahrenheit - 32) * 5 / 9).toInt().toString()
}

fun formatDateTimestamp(
    timestamp: Timestamp
): String{
    val date = DateFormat.getDateInstance()
        .format(timestamp.toDate())
        .toString().split(",")[0]

    return date
}


