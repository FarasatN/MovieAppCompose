package com.farasatnovruzov.newsappoffline.core.util

fun String.escapeSqlLike(): String {
    return this.replace("\\", "\\\\") // Əvvəlcə \ işarəsini qoruyuruq
        .replace("%", "\\%")  // % işarəsini escape edirik
        .replace("_", "\\_")  // _ işarəsini escape edirik
}