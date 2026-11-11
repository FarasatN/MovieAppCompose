package com.farasatnovruzov.newsappoffline.news.presentation.util

import com.farasatnovruzov.newsappoffline.core.domain.NewsLanguage

val NewsLanguage.flagEmoji: String
    get() = when (this) {
        NewsLanguage.AZERBAIJANI -> "🇦🇿"
        NewsLanguage.ENGLISH -> "🇬🇧"
        NewsLanguage.RUSSIAN -> "🇷🇺"
        NewsLanguage.TURKISH -> "🇹🇷"
        NewsLanguage.SPANISH -> "🇪🇸"
        NewsLanguage.FRENCH -> "🇫🇷"
        NewsLanguage.GERMAN -> "🇩🇪"
        NewsLanguage.ARABIC -> "🇸🇦"
        NewsLanguage.CHINESE -> "🇨🇳"
        NewsLanguage.JAPANESE -> "🇯🇵"
    }

/** Dilin öz adı (endonim) – interfeys dilindən asılı olaraq tərcümə olunmur. */
val NewsLanguage.displayName: String
    get() = when (this) {
        NewsLanguage.AZERBAIJANI -> "Azərbaycan"
        NewsLanguage.ENGLISH -> "English"
        NewsLanguage.RUSSIAN -> "Русский"
        NewsLanguage.TURKISH -> "Türkçe"
        NewsLanguage.SPANISH -> "Español"
        NewsLanguage.FRENCH -> "Français"
        NewsLanguage.GERMAN -> "Deutsch"
        NewsLanguage.ARABIC -> "العربية"
        NewsLanguage.CHINESE -> "中文"
        NewsLanguage.JAPANESE -> "日本語"
    }
