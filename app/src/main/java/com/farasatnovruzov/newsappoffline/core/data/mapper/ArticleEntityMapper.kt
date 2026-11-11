package com.farasatnovruzov.newsappoffline.core.data.mapper

import com.farasatnovruzov.newsappoffline.core.data.local.room.ArticleEntity
import com.farasatnovruzov.newsappoffline.core.domain.Article

fun ArticleEntity.toArticle(): Article = Article(
    aiOrg = aiOrg,
    aiRegion = aiRegion,
    aiSummary = aiSummary,
    aiTag = aiTag,
    articleId = articleId,
    category = category,
    content = content,
    country = country,
    creator = creator,
    datatype = datatype,
    description = description,
    duplicate = duplicate,
    fetchedAt = fetchedAt,
    imageUrl = imageUrl,
    keywords = keywords,
    language = language,
    link = link,
    pubDate = pubDate,
    pubDateTZ = pubDateTZ,
    sentiment = sentiment,
    sentimentStats = sentimentStats,
    sourceIcon = sourceIcon,
    sourceId = sourceId,
    sourceName = sourceName,
    sourcePriority = sourcePriority,
    sourceUrl = sourceUrl,
    title = title,
    videoUrl = videoUrl
)

/**
 * @param languageCode verilibsə, cache-də dilə görə filtrləmək üçün məqalənin `language` sahəsinə yazılır
 * (API-nin özü bu sahəni "azerbaijani" kimi ad ilə qaytarır, biz isə "az" kodu ilə saxlayırıq).
 */
fun Article.toArticleEntity(languageCode: String? = null): ArticleEntity = ArticleEntity(
    aiOrg = aiOrg,
    aiRegion = aiRegion,
    aiSummary = aiSummary,
    aiTag = aiTag,
    articleId = articleId,
    category = category,
    content = content,
    country = country,
    creator = creator,
    datatype = datatype,
    description = description,
    duplicate = duplicate,
    fetchedAt = fetchedAt,
    imageUrl = imageUrl,
    keywords = keywords,
    language = languageCode ?: language,
    link = link,
    pubDate = pubDate,
    pubDateTZ = pubDateTZ,
    sentiment = sentiment,
    sentimentStats = sentimentStats,
    sourceIcon = sourceIcon,
    sourceId = sourceId,
    sourceName = sourceName,
    sourcePriority = sourcePriority,
    sourceUrl = sourceUrl,
    title = title,
    videoUrl = videoUrl
)
