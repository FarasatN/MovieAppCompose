package com.farasatnovruzov.newsappoffline.core.domain

/** [code] – API-yə göndərilən dil kodudur. Ekranda görünən ad/bayraq presentation qatındadır. */
enum class NewsLanguage(val code: String) {
    AZERBAIJANI("az"),
    ENGLISH("en"),
    RUSSIAN("ru"),
    TURKISH("tr"),
    SPANISH("es"),
    FRENCH("fr"),
    GERMAN("de"),
    ARABIC("ar"),
    CHINESE("zh"),
    JAPANESE("ja")
}
