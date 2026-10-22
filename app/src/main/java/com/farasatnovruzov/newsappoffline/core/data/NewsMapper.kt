package com.farasatnovruzov.newsappoffline.core.data

import com.farasatnovruzov.newsappoffline.core.data.local.room.ArticleEntity
import com.farasatnovruzov.newsappoffline.core.data.remote.ArticleDto
import com.farasatnovruzov.newsappoffline.core.data.remote.NewsListDto
import com.farasatnovruzov.newsappoffline.core.domain.Article
import com.farasatnovruzov.newsappoffline.core.domain.NewsList
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive

fun JsonElement?.toFlexibleString(): String? {
    return when (this) {
        null, JsonNull -> null
        is JsonPrimitive -> if (isString) content else toString()
        else -> toString()
    }
}

fun NewsListDto.toNewsList(): NewsList {
    return NewsList(
        nextPage = nextPage,
        articles = results?.map { it?.toArticle() } ?: emptyList(),
        status = status,
        totalResults = totalResults
    )
}

fun ArticleDto.toArticle(): Article {
    return Article(
        aiOrg = aiOrg.toFlexibleString(),
        aiRegion = aiRegion.toFlexibleString(),
        aiSummary = aiSummary.toFlexibleString(),
        aiTag = aiTag.toFlexibleString(),
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
        sentimentStats = sentimentStats.toFlexibleString(),
        sourceIcon = sourceIcon,
        sourceId = sourceId,
        sourceName = sourceName,
        sourcePriority = sourcePriority,
        sourceUrl = sourceUrl,
        title = title,
        videoUrl = videoUrl
    )
}

fun ArticleEntity.toArticle(): Article {
    return Article(
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
}

fun Article.toArticleEntity(): ArticleEntity {
    return ArticleEntity(
        aiOrg = aiOrg,
        aiRegion = aiRegion,
        aiSummary = aiSummary,
        aiTag = aiTag,
        articleId = articleId ?: "",
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
}
