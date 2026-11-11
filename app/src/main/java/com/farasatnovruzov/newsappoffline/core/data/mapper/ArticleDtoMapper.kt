package com.farasatnovruzov.newsappoffline.core.data.mapper

import com.farasatnovruzov.newsappoffline.core.data.remote.ArticleDto
import com.farasatnovruzov.newsappoffline.core.data.remote.NewsListDto
import com.farasatnovruzov.newsappoffline.core.domain.Article
import com.farasatnovruzov.newsappoffline.core.domain.NewsList
import java.util.UUID

fun NewsListDto.toNewsList(): NewsList = NewsList(
    articles = results.orEmpty().filterNotNull().map { it.toArticle() },
    nextPage = nextPage
)

fun ArticleDto.toArticle(): Article = Article(
    aiOrg = aiOrg.toFlexibleString(),
    aiRegion = aiRegion.toFlexibleString(),
    aiSummary = aiSummary.toFlexibleString(),
    aiTag = aiTag.toFlexibleString(),
    // API id qaytarmasa: link, o da yoxdursa təsadüfi id. Domain-də id həmişə mövcuddur.
    articleId = articleId ?: link ?: UUID.randomUUID().toString(),
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
