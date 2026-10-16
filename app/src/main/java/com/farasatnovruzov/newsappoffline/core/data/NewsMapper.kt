package com.farasatnovruzov.newsappoffline.core.data

import com.farasatnovruzov.newsappoffline.core.data.local.room.ArticleEntity
import com.farasatnovruzov.newsappoffline.core.data.remote.ArticleDto
import com.farasatnovruzov.newsappoffline.core.data.remote.NewsListDto
import com.farasatnovruzov.newsappoffline.core.domain.Article
import com.farasatnovruzov.newsappoffline.core.domain.NewsList

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
        aiOrg = aiOrg,
        aiRegion = aiRegion,
        aiSummary = aiSummary,
        aiTag = aiTag,
        articleId = articleId,
        category = category,
        content = "$articleId: $content",
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

fun ArticleEntity.toArticle(): Article {
    return Article(
        aiOrg = aiOrg,
        aiRegion = aiRegion,
        aiSummary = aiSummary,
        aiTag = aiTag,
        articleId = articleId,
        category = category,
        content = "$articleId: $content",
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
        articleId = articleId,
        category = category,
        content = "$articleId: $content",
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