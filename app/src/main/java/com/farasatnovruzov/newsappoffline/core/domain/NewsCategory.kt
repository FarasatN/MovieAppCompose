package com.farasatnovruzov.newsappoffline.core.domain

enum class NewsCategory(
    val code: String?,
    val displayName: String,
    val emoji: String,
    val description: String
) {
    ALL(null, "Bütün", "🌐", "Bütün xəbərlər"),
    BUSINESS("business", "Business", "💼", "Finance, markets, and corporate updates."),
    CRIME("crime", "Crime", "⚖️", "Law enforcement and legal incidents."),
    DOMESTIC("domestic", "Domestic", "🏠", "Regional and local events."),
    EDUCATION("education", "Education", "🎓", "Academic research and school reforms."),
    ENTERTAINMENT("entertainment", "Entertainment", "🎬", "Celebrities, movies, music, and media."),
    ENVIRONMENT("environment", "Environment", "🌱", "Climate change and conservation."),
    FOOD("food", "Food", "🍕", "Culinary trends and industry news."),
    HEALTH("health", "Health", "🏥", "Medical discoveries and wellness."),
    LIFESTYLE("lifestyle", "Lifestyle", "✨", "Living, culture, and personal wellness."),
    POLITICS("politics", "Politics", "🏛️", "Government policies and geopolitics."),
    SCIENCE("science", "Science", "🔬", "Scientific discoveries and technical breakthroughs."),
    SPORTS("sports", "Sports", "⚽", "Athletic events, games, and rankings."),
    TECHNOLOGY("technology", "Technology", "💻", "Artificial intelligence, software, and innovations."),
    TOP("top", "Top", "🔥", "Trending and breaking global updates."),
    TOURISM("tourism", "Tourism", "✈️", "Travel trends and hospitality."),
    WORLD("world", "World", "🌍", "International affairs and global topics."),
    OTHER("other", "Other", "📌", "Uncategorized or niche topics.")
}
