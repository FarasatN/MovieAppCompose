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
    SPANISH("es", "Español", "🇪🇸"),
    FRENCH("fr", "Français", "🇫🇷"),
    GERMAN("de", "Deutsch", "🇩🇪"),
    ARABIC("ar", "العربية", "🇸🇦"),
    CHINESE("zh", "中文", "🇨🇳"),
    JAPANESE("ja", "日本語", "🇯🇵"),
}
