package com.farasatnovruzov.movieappcompose.util

import androidx.room.TypeConverter
import java.util.Date
import java.util.UUID

class Converters {

    @TypeConverter
    fun fromUUID(uuid: UUID): String = uuid.toString()

    @TypeConverter
    fun toUUID(uuidString: String): UUID = UUID.fromString(uuidString)

    @TypeConverter
    fun fromDate(date: Date): Long = date.time

    @TypeConverter
    fun toDate(timestamp: Long): Date = Date(timestamp)

}