package com.farasatnovruzov.newsappoffline.core.data.local.room

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class ArticleTypeConverters {

    @TypeConverter
    fun fromStringList(list: List<String?>?): String? =
        list?.let { runCatching { Json.encodeToString(it) }.getOrNull() }

    @TypeConverter
    fun toStringList(value: String?): List<String?>? =
        value?.let { runCatching { Json.decodeFromString<List<String?>>(it) }.getOrNull() }
}
