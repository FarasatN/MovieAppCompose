package com.farasatnovruzov.newsappoffline.core.data.mapper

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive

/** API bəzi sahələri bəzən string, bəzən obyekt/massiv qaytarır; hamısını String-ə çevirir. */
fun JsonElement?.toFlexibleString(): String? = when (this) {
    null, JsonNull -> null
    is JsonPrimitive -> content
    else -> toString()
}
