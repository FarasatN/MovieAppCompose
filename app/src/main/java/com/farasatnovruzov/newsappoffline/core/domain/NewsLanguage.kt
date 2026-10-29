package com.farasatnovruzov.newsappoffline.core.domain

enum class NewsLanguage(
    val code: String,
    val displayName: String,
    val flagEmoji: String
) {
    AZERBAIJANI("az", "Azərbaycan", "🇦🇿"),
    ENGLISH("en", "English", "🇬🇧"),
    RUSSIAN("ru", "Русский", "🇷🇺"),
    TURKISH("tr", "Türkçe", "🇹🇷"),
    SPANISH("es", "Español", "🇪🇸")
}
