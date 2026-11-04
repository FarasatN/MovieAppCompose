package com.farasatnovruzov.newsappoffline.news.presentation.util

import androidx.annotation.StringRes
import com.farasatnovruzov.movieappcompose.R
import com.farasatnovruzov.newsappoffline.core.domain.NewsCategory

val NewsCategory.emoji: String
    get() = when (this) {
        NewsCategory.ALL -> "🌐"
        NewsCategory.BUSINESS -> "💼"
        NewsCategory.CRIME -> "⚖️"
        NewsCategory.DOMESTIC -> "🏠"
        NewsCategory.EDUCATION -> "🎓"
        NewsCategory.ENTERTAINMENT -> "🎬"
        NewsCategory.ENVIRONMENT -> "🌱"
        NewsCategory.FOOD -> "🍕"
        NewsCategory.HEALTH -> "🏥"
        NewsCategory.LIFESTYLE -> "✨"
        NewsCategory.POLITICS -> "🏛️"
        NewsCategory.SCIENCE -> "🔬"
        NewsCategory.SPORTS -> "⚽"
        NewsCategory.TECHNOLOGY -> "💻"
        NewsCategory.TOP -> "🔥"
        NewsCategory.TOURISM -> "✈️"
        NewsCategory.WORLD -> "🌍"
        NewsCategory.OTHER -> "📌"
    }

@get:StringRes
val NewsCategory.titleRes: Int
    get() = when (this) {
        NewsCategory.ALL -> R.string.category_all
        NewsCategory.BUSINESS -> R.string.category_business
        NewsCategory.CRIME -> R.string.category_crime
        NewsCategory.DOMESTIC -> R.string.category_domestic
        NewsCategory.EDUCATION -> R.string.category_education
        NewsCategory.ENTERTAINMENT -> R.string.category_entertainment
        NewsCategory.ENVIRONMENT -> R.string.category_environment
        NewsCategory.FOOD -> R.string.category_food
        NewsCategory.HEALTH -> R.string.category_health
        NewsCategory.LIFESTYLE -> R.string.category_lifestyle
        NewsCategory.POLITICS -> R.string.category_politics
        NewsCategory.SCIENCE -> R.string.category_science
        NewsCategory.SPORTS -> R.string.category_sports
        NewsCategory.TECHNOLOGY -> R.string.category_technology
        NewsCategory.TOP -> R.string.category_top
        NewsCategory.TOURISM -> R.string.category_tourism
        NewsCategory.WORLD -> R.string.category_world
        NewsCategory.OTHER -> R.string.category_other
    }
