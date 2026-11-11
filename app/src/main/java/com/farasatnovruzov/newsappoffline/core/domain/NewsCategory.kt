package com.farasatnovruzov.newsappoffline.core.domain

/** [code] – API-yə göndərilən dəyərdir; null "bütün kateqoriyalar" deməkdir. */
enum class NewsCategory(val code: String?) {
    ALL(null),
    BUSINESS("business"),
    CRIME("crime"),
    DOMESTIC("domestic"),
    EDUCATION("education"),
    ENTERTAINMENT("entertainment"),
    ENVIRONMENT("environment"),
    FOOD("food"),
    HEALTH("health"),
    LIFESTYLE("lifestyle"),
    POLITICS("politics"),
    SCIENCE("science"),
    SPORTS("sports"),
    TECHNOLOGY("technology"),
    TOP("top"),
    TOURISM("tourism"),
    WORLD("world"),
    OTHER("other")
}
