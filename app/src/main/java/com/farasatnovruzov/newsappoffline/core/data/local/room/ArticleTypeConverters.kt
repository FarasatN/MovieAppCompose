package com.farasatnovruzov.newsappoffline.core.data.local.room

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class ArticleTypeConverters {

    @TypeConverter
    fun fromStringList(list: List<String?>?): String? {
        return list?.let {
            try {
                Json.encodeToString(it)
            } catch (_: Exception) {
                null
            }
        }
    }

    @TypeConverter
    fun toStringList(value: String?): List<String?>? {
        return value?.let {
            try {
                Json.decodeFromString<List<String?>>(it)
            } catch (_: Exception) {
                null
            }
        }
    }
}
